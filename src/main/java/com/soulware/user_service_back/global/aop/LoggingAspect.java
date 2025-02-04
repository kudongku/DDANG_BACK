package com.soulware.user_service_back.global.aop;

import static com.soulware.user_service_back.global.util.MaskingUtil.maskSensitiveData;

import java.lang.reflect.Method;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void cut() {
    }

    @Around("cut()")
    public Object aroundLog(
        ProceedingJoinPoint proceedingJoinPoint
    ) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.getName().equals("openapiJson")) {
            return proceedingJoinPoint.proceed();
        }

//      API Request
        log.info("=== Executing Method: {} ===", method.getName());

        Object[] args = proceedingJoinPoint.getArgs();

        if (args == null || args.length == 0) {
            log.info("No parameters passed to the method.");
        } else {
            for (Object arg : args) {
                log.info(
                    "Parameter type = {}, Parameter value = {}",
                    arg.getClass().getSimpleName(),
                    maskSensitiveData(arg)
                );
            }
        }

        long startTime = System.currentTimeMillis();

//        Proceed
        Object returnValue = proceedingJoinPoint.proceed();

//        Response
        long endTime = System.currentTimeMillis();
        log.info("Execution time of {}: {} ms", method.getName(), endTime - startTime);

        if (Objects.isNull(returnValue)) {
            return null;
        }

        String returnTypeToString;
        String returnValueToString;

        if (returnValue instanceof ResponseEntity<?> responseEntity
            && Objects.nonNull(responseEntity.getBody())
        ) {
            Object responseEntityBody = responseEntity.getBody();
            returnTypeToString = responseEntityBody.getClass().getSimpleName();
            returnValueToString = maskSensitiveData(responseEntityBody).toString();
        } else {
            returnTypeToString = returnValue.getClass().getSimpleName();
            returnValueToString = maskSensitiveData(returnValue).toString();
        }

        log.info(
            "Return type = {}, Return value = {}",
            returnTypeToString,
            returnValueToString
        );

        return returnValue;
    }

}
