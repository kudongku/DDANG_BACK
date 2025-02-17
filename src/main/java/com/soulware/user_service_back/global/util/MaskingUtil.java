package com.soulware.user_service_back.global.util;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class MaskingUtil {

    public static Object maskSensitiveData(@NotNull Object arg) {
        if (
            arg instanceof String
                || arg instanceof UsernamePasswordAuthenticationToken
                || arg instanceof UUID
        ) {
            return arg;
        }

        Class<?> responseDtoClass = arg.getClass();
        Object maskedInstance;

        try {
            maskedInstance = responseDtoClass.getDeclaredConstructor().newInstance();

            for (Field field : responseDtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(arg);

                if (field.isAnnotationPresent(Sensitive.class)) {
                    field.set(maskedInstance, "******");
                } else {
                    field.set(maskedInstance, value);
                }
            }
        } catch (
            NoSuchMethodException
            | InvocationTargetException
            | InstantiationException
            | IllegalAccessException e
        ) {
            throw new RuntimeException("Error masking sensitive data", e);
        }

        return maskedInstance;
    }

}
