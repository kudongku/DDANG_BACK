package com.soulware.user_service_back.global.handler;

import com.soulware.user_service_back.global.exception.CustomIllegalArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j(topic = "ExceptionHandler")
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<String> handleCustomIllegalArgumentsException(
        CustomIllegalArgumentException e
    ) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
