package com.khan.wardroby.exception;

public class DatabaseException extends WardrobyBaseException{
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
