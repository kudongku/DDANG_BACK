package com.soulware.user_service_back.global.util;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.springframework.stereotype.Component;

@Component
public class MaskingUtil {

    public Object maskSensitiveData(@NotNull Object arg) {
        if (arg instanceof String) {
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
