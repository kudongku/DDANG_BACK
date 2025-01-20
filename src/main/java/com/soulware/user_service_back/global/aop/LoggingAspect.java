package com.soulware.user_service_back.global.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
        log.info("======= method name = {} =======", method.getName());

        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length == 0) {
            log.info("no parameter");
        }
        for (Object arg : args) {
            log.info(
                "parameter type = {}, parameter value = {}",
                arg.getClass().getSimpleName(),
                arg
            );
        }

        Object returnObj = proceedingJoinPoint.proceed();

        if (returnObj != null) {
            log.info(
                "return type = {}, return value = {}",
                returnObj.getClass().getSimpleName(),
                returnObj
            );
        }

        return returnObj;
    }

}
