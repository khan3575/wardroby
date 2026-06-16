package com.khan.wardroby.exception;

public class AuthorityNotFoundException extends AuthException{
    public AuthorityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorityNotFoundException(String message) {
        super(message);
    }

    public AuthorityNotFoundException() {
    }
}
