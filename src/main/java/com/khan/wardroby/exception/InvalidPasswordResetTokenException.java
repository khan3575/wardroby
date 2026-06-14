package com.khan.wardroby.exception;

public class InvalidPasswordResetTokenException extends RuntimeException{
    public InvalidPasswordResetTokenException() {
    }

    public InvalidPasswordResetTokenException(String message) {
        super(message);
    }

    public InvalidPasswordResetTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
