package com.khan.wardroby.exception;

public class TokenUsedException extends RuntimeException{
    public TokenUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenUsedException(String message) {
        super(message);
    }

    public TokenUsedException() {
    }
}
