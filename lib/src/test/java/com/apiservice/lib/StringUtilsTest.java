package com.apiservice.lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    void capitalizeUppercasesFirstLetter() {
        assertEquals("Alice", StringUtils.capitalize("alice"));
    }
}
