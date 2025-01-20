package com.soulware.user_service_back.global.exception;

public class ExistEmailException extends IllegalArgumentException {

    public ExistEmailException(final String message){
        super(message);
    }
}
