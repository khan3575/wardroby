package com.khan.wardroby.exception;

public class PasswordResetTokenExpiredException extends AuthException{
    public PasswordResetTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetTokenExpiredException(String message) {
        super(message);
    }

    public PasswordResetTokenExpiredException() {
    }
}
