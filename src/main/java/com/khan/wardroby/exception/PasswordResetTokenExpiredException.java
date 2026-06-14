package com.khan.wardroby.exception;

public class PasswordResetTokenExpiredException extends RuntimeException{
    public PasswordResetTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetTokenExpiredException(String message) {
        super(message);
    }

    public PasswordResetTokenExpiredException() {
    }
}
