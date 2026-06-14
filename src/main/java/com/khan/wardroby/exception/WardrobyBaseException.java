package com.khan.wardroby.exception;

public class WardrobyBaseException extends RuntimeException{


    public WardrobyBaseException() {
    }

    public WardrobyBaseException(String message) {
        super(message);
    }

    public WardrobyBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
