package com.soulware.user_service_back.global.exception;

public class CustomTokenExpiredException extends RuntimeException {

    public CustomTokenExpiredException(String message) {
        super(message);
    }

}
