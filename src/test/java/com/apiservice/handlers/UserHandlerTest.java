package com.apiservice.handlers;

import com.apiservice.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserHandlerTest {

    @Test
    void listUsersReturnsSeededUsers() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));
        handler.getUsers().put(2, new User(2, "Bob", "bob@example.com"));

        ApiResponse response = handler.listUsers("GET");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<?> body = (List<?>) response.getBody();
        assertEquals(2, body.size());
    }

    @Test
    void getUserReturnsKnownUser() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));

        ApiResponse response = handler.getUser("GET", "1");

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
        User user = (User) response.getBody();
        assertEquals("Alice", user.getName());
    }

    @Test
    void getUserReturns404ForMissingUser() {
        UserHandler handler = new UserHandler();
        ApiResponse response = handler.getUser("GET", "999");
        assertEquals(404, response.getStatusCode());
    }

    @Test
    void createUserStoresValidUser() {
        UserHandler handler = new UserHandler();
        User user = new User(1, "Charlie", "charlie@example.com");

        ApiResponse response = handler.createUser("POST", user);

        assertEquals(201, response.getStatusCode());
        assertTrue(handler.getUsers().containsKey(1));
    }

    @Test
    void createUserRejectsInvalidPayload() {
        UserHandler handler = new UserHandler();
        User user = new User(0, "", "");

        ApiResponse response = handler.createUser("POST", user);

        assertEquals(400, response.getStatusCode());
    }

    @Test
    void createUserRejectsConflict() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));
        User dup = new User(1, "Bob", "bob@example.com");

        ApiResponse response = handler.createUser("POST", dup);

        assertEquals(409, response.getStatusCode());
    }

    @Test
    void updateUserOverwritesExistingUser() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));

        User update = new User(0, "Alice Updated", "alice.updated@example.com");
        ApiResponse response = handler.updateUser("PUT", "1", update);

        assertEquals(200, response.getStatusCode());
        User updated = handler.getUsers().get(1);
        assertEquals("Alice Updated", updated.getName());
    }

    @Test
    void updateUserReturns404ForMissingUser() {
        UserHandler handler = new UserHandler();
        User update = new User(0, "Bob", "bob@example.com");

        ApiResponse response = handler.updateUser("PUT", "999", update);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    void searchUsersByNameReturnsMatching() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));
        handler.getUsers().put(2, new User(2, "Bob", "bob@example.com"));
        handler.getUsers().put(3, new User(3, "Alicia", "alicia@example.com"));

        ApiResponse response = handler.searchUsersByName("GET", "ali");

        assertEquals(200, response.getStatusCode());
        List<?> body = (List<?>) response.getBody();
        assertEquals(2, body.size());
    }

    @Test
    void searchUsersByNameRejectsEmptyQuery() {
        UserHandler handler = new UserHandler();

        ApiResponse response = handler.searchUsersByName("GET", "");

        assertEquals(400, response.getStatusCode());
    }

    @Test
    void getUsersByEmailDomainFiltersByDomain() {
        UserHandler handler = new UserHandler();
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));
        handler.getUsers().put(2, new User(2, "Bob", "bob@other.com"));
        handler.getUsers().put(3, new User(3, "Carol", "carol@example.com"));

        ApiResponse response = handler.getUsersByEmailDomain("GET", "example.com");

        assertEquals(200, response.getStatusCode());
        List<?> body = (List<?>) response.getBody();
        assertEquals(2, body.size());
    }

    @Test
    void countReturnsSize() {
        UserHandler handler = new UserHandler();
        assertEquals(0, handler.count());
        handler.getUsers().put(1, new User(1, "Alice", "alice@example.com"));
        handler.getUsers().put(2, new User(2, "Bob", "bob@example.com"));
        assertEquals(2, handler.count());
    }
}
