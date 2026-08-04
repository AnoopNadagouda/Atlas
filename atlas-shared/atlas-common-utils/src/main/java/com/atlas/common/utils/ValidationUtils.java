package com.atlas.common.utils;

import com.atlas.domain.exception.ValidationException;

public final class ValidationUtils {

    private ValidationUtils() {}

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ValidationException(message);
        }
    }

    public static void notBlank(String str, String message) {
        if (StringUtils.isNullOrBlank(str)) {
            throw new ValidationException(message);
        }
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }
}
