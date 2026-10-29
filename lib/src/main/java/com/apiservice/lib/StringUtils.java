package com.apiservice.lib;

public final class StringUtils {

    private StringUtils() {
    }

    public static String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public static String reverse(String value) {
        if (value == null) {
            return null;
        }
        return new StringBuilder(value).reverse().toString();
    }
}
