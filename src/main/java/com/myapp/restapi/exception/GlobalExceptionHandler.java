package com.myapp.restapi.exception;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;




@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	protected final Log logger = LogFactory.getLog(getClass());
	

	private ProblemDetail body;
	
	    @ExceptionHandler({
	    	EntityExistsException.class,
	    	EntityNotFoundException.class,
	    	InternalException.class,
	    	ConstraintViolationException.class,
	    	DataIntegrityViolationException.class,
	    	BusinessException.class
	    })
	    public ResponseEntity<Object>  handleCustomerException(Exception ex, WebRequest request)  throws Exception{
	    	if (ex instanceof EntityExistsException subEx) {
				return handleEntityExistsException(subEx, null, HttpStatus.CONFLICT, request);
			}
			else if (ex instanceof EntityNotFoundException subEx) {
				return handleEntityNotFoundException(subEx, null, HttpStatus.NOT_FOUND, request);
			}
			else if (ex instanceof InternalException subEx) {
				return handleInternalException(subEx, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
			}
			else if (ex instanceof ConstraintViolationException subEx) {
				return handleConstraintViolationException(subEx, null, HttpStatus.BAD_REQUEST, request);
			}
			else if (ex instanceof BusinessException subEx) {
				return handleBusinessException(subEx, null, HttpStatus.BAD_REQUEST, request);
			}
			else {
				throw ex;
			}
	    	
	    }
	    
	    private ResponseEntity<Object> handleEntityExistsException(
	    		EntityExistsException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	
	    	body= createProblemDetail(ex, status, ex.getLocalizedMessage(), ex.getLocalizedMessage(),ex.getArgs() , request);
	    	body.setType(URI.create("/errors"));
			return handleExceptionInternal(ex, body, headers, status, request);
		
		}
	    
	    private ResponseEntity<Object> handleEntityNotFoundException(
	    		EntityNotFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	body= createProblemDetail(ex, status, ex.getLocalizedMessage(), ex.getLocalizedMessage(),ex.getArgs() , request);
	    	body.setType(URI.create("/errors"));
			return handleExceptionInternal(ex, body, headers, status, request);
		}
	    private ResponseEntity<Object> handleInternalException(
	    		InternalException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	
	    	body= createProblemDetail(ex, status, ex.getLocalizedMessage(), ex.getLocalizedMessage(),null , request);
	    	body.setType(URI.create("/errors"));
			return handleExceptionInternal(ex, body, headers, status, request);
		
		}
	    
	    private ResponseEntity<Object> handleConstraintViolationException(
	    		ConstraintViolationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	
	    	body= createProblemDetail(ex, status, ex.getLocalizedMessage(), ex.getLocalizedMessage(),null , request);
	    	body.setType(URI.create("/errors"));
	    	body.setDetail("Invalid request content.");
	    	ex.getConstraintViolations().forEach( 
	    			voilation -> {
	    				StringBuilder tmp = new StringBuilder(); 
	    				voilation.getPropertyPath().forEach(node ->{	    					
	    				if(node.getKind() != ElementKind.METHOD ) {
	    					 tmp.append(node.toString()).append(".");	
	    				}
	    				});
	    				if (tmp.length() > 0 && tmp.charAt(tmp.length() - 1) == '.') {
	    			        tmp.deleteCharAt(tmp.length() - 1);  // Remove the last period (optional, based on your requirement)
	    			    }
	    				body.setProperty(tmp.toString(),voilation.getMessage());
	    				body.setDetail(body.getDetail().concat("; ").concat(tmp.toString().concat(":").concat(voilation.getMessage().toString())));
	    				
	    				
	    			});
			return handleExceptionInternal(ex, body, headers, status, request);
		
		}
	    
	    private ResponseEntity<Object> handleBusinessException(
	    		BusinessException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    	
	    	body= createProblemDetail(ex, status, ex.getLocalizedMessage(), ex.getLocalizedMessage(),ex.getArgs() , request);
	    	body.setProperty("errorCode", Integer.parseInt(ex.getArgs()[0]));
	    	body.setProperty("errorMessage", body.getDetail());
	    	body.setDetail(ex.getArgs()[0]+":"+body.getDetail());
	    	body.setType(URI.create("/errors"));
			return handleExceptionInternal(ex, body, headers, status, request);
		
		}
	    
	    /**
	     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
	     *
	     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
	     * @param headers HttpHeaders
	     * @param status  HttpStatus
	     * @param request WebRequest
	     * @return the ResponseEntity object
	     */
	   @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatusCode status,WebRequest request) {
	      
	      body = ex.getBody();
		   ex.getBindingResult().getFieldErrors()
	      		.stream()
	      		.forEach(error -> {
	      			body.setProperty(error.getField(), error.getDefaultMessage());
	      			body.setDetail(body.getDetail() + "; "+error.getField()+":"+ error.getDefaultMessage());
	      			});
		   body.setType(URI.create("/errors"));
	      return handleExceptionInternal(ex,null, headers, status, request);
	    }
	    
	
	
}
