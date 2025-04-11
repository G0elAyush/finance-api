package com.myapp.restapi.exception;

public class InternalException  extends Exception{

	private static final long serialVersionUID = -1455188343431173109L;
	
	public InternalException() {
        super();
    }
	
	public InternalException(String message) {
        super(message);
        
    }
	
	public InternalException(String message,Throwable cause) {
        super(message, cause);
    }
	
	public InternalException(Throwable cause) {
        super(cause);
    }

}
