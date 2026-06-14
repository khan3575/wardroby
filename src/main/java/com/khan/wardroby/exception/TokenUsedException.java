package com.khan.wardroby.exception;

public class TokenUsedException extends AuthException{
    public TokenUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenUsedException(String message) {
        super(message);
    }

    public TokenUsedException() {
    }
}
