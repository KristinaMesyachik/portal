package com.example.demo.exception;

public class NoSuchPortalException extends RuntimeException{
    public NoSuchPortalException() {
    }

    public NoSuchPortalException(String message) {
        super(message);
    }

    public NoSuchPortalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPortalException(Throwable cause) {
        super(cause);
    }

    public NoSuchPortalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
