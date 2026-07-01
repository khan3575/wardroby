package com.khan.wardroby.exception;

public class ItemException extends WardrobyBaseException{
    public ItemException() {
    }

    public ItemException(String message) {
        super(message);
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
