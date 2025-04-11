package com.myapp.restapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends Exception{
	
	private static final long serialVersionUID = 3892483727936093252L;
	
	
	private String [] args = null;

	public BusinessException() {
        super();
    }
	
	public BusinessException(String message,String ... nargs) {
        super(message);
        this.args = nargs;
        
    }
	
	public BusinessException(String message,Throwable cause,String ... nargs) {
        super(message, cause);
        this.args = nargs;
    }
	
	
	public BusinessException(Throwable cause) {
        super(cause);
    }

}
