package com.soulware.user_service_back.global.util;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import jakarta.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MaskingUtil {

    private final static String MASKING_VALUE = "******";

    public static Object maskSensitiveData(@NotNull Object arg) {
        Class<?> responseDtoClass = arg.getClass();
        Object maskedInstance;

        try {
            maskedInstance = responseDtoClass.getDeclaredConstructor().newInstance();

            for (Field field : responseDtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(arg);

                if (field.isAnnotationPresent(Sensitive.class)) {
                    field.set(maskedInstance, MASKING_VALUE);
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
            return MASKING_VALUE;
        }

        return maskedInstance;
    }

}
