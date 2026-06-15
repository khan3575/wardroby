package com.khan.wardroby.exception;

public class InvalidPasswordException extends AuthException{
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException() {
    }
}
