package com.khan.wardroby.exception;

public class InvalidItemDefinitionException extends ItemException{
    public InvalidItemDefinitionException() {
    }

    public InvalidItemDefinitionException(String message) {
        super(message);
    }

    public InvalidItemDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
