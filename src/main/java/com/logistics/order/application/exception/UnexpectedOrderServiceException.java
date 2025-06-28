package com.logistics.order.application.exception;


public class UnexpectedOrderServiceException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
    public UnexpectedOrderServiceException(String message, Exception ex) {
            super(message, ex); 
        }


}
