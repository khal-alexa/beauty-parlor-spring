package com.parlor.booking.service.exceptions;

public class EntityNotSavedException extends RuntimeException {
    public EntityNotSavedException() {
        super();
    }

    public EntityNotSavedException(String message) {
        super(message);
    }

    public EntityNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
