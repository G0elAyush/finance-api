package com.myapp.restapi.exception;

import jakarta.persistence.PersistenceException;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class EntityExistsException  extends PersistenceException{

	private static final long serialVersionUID = 1L;
	
    private String [] args;
  
    
    /**
     * Constructs a new <code>EntityExistsException</code> exception with
     * <code>null</code> as its detail message.
     */
    public EntityExistsException() {
        super();
    }

    /**
     * Constructs a new <code>EntityExistsException</code> exception with the
     * specified detail message.
     * 
     * @param message
     *            the detail message.
     */
    public EntityExistsException(String message, String... nargs) {
        super(message);
        this.args = nargs;
    }

    /**
     * Constructs a new <code>EntityExistsException</code> exception with the
     * specified detail message and cause.
     * 
     * @param message
     *            the detail message.
     * @param cause
     *            the cause.
     */
    public EntityExistsException(String message, String [] nargs,Throwable cause) {
        super(message, cause);
        this.args = nargs;
    }

    /**
     * Constructs a new <code>EntityExistsException</code> exception with the
     * specified cause.
     * 
     * @param cause
     *            the cause.
     */
    public EntityExistsException(Throwable cause) {
        super(cause);
    }

}
