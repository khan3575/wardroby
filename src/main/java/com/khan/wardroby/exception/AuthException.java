package com.khan.wardroby.exception;

public class AuthException extends WardrobyBaseException{

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
        super();
    }
}
