package com.apiservice.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void newUserPopulatesFields() {
        User user = new User(1, "John Doe", "john@example.com");
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void validateAcceptsCompleteUser() {
        User user = new User(1, "John", "john@example.com");
        assertTrue(user.validate());
    }

    @Test
    void validateRejectsZeroId() {
        User user = new User(0, "John", "john@example.com");
        assertFalse(user.validate());
    }

    @Test
    void validateRejectsEmptyName() {
        User user = new User(1, "", "john@example.com");
        assertFalse(user.validate());
    }

    @Test
    void validateRejectsEmptyEmail() {
        User user = new User(1, "John", "");
        assertFalse(user.validate());
    }
}
