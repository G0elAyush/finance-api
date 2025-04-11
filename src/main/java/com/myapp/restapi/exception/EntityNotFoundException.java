package com.myapp.restapi.exception;

import jakarta.persistence.PersistenceException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EntityNotFoundException  extends PersistenceException{

	private static final long serialVersionUID = 1L;
	
    private String [] args;
  
    
    /**
     * Constructs a new <code>EntityNotFoundException</code> exception with
     * <code>null</code> as its detail message.
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructs a new <code>EntityNotFoundException</code> exception with the
     * specified detail message.
     * 
     * @param message
     *            the detail message.
     */
    public EntityNotFoundException(String message, String... nargs) {
        super(message);
        this.args = nargs;
    }

    /**
     * Constructs a new <code>EntityNotFoundException</code> exception with the
     * specified detail message and cause.
     * 
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public EntityNotFoundException(String message, String [] args,Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new <code>EntityNotFoundException</code> exception with the
     * specified cause.
     * 
     * @param cause
     *            the cause.
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

}
