package com.soulware.user_service_back.global.exception;

public class CustomNotAuthenticatedException extends RuntimeException {

    public CustomNotAuthenticatedException(String message) {
        super(message);
    }

}
