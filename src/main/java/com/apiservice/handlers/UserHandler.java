package com.apiservice.handlers;

import com.apiservice.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHandler {
    private final Map<Integer, User> users = new HashMap<>();

    public Map<Integer, User> getUsers() {
        return users;
    }

    public ApiResponse listUsers(String method) {
        if (!"GET".equalsIgnoreCase(method)) {
            return new ApiResponse(405, "Method not allowed");
        }
        List<User> result = new ArrayList<>(users.values());
        return new ApiResponse(200, result);
    }

    public ApiResponse getUser(String method, String pathId) {
        if (!"GET".equalsIgnoreCase(method)) {
            return new ApiResponse(405, "Method not allowed");
        }
        Integer id = parseId(pathId);
        if (id == null) {
            return new ApiResponse(400, "Invalid user ID");
        }
        User user = users.get(id);
        if (user == null) {
            return new ApiResponse(404, "User not found");
        }
        return new ApiResponse(200, user);
    }

    public ApiResponse createUser(String method, User user) {
        if (!"POST".equalsIgnoreCase(method)) {
            return new ApiResponse(405, "Method not allowed");
        }
        if (user == null) {
            return new ApiResponse(400, "Invalid request body");
        }
        if (!user.validate()) {
            return new ApiResponse(400, "Invalid user data");
        }
        if (users.containsKey(user.getId())) {
            return new ApiResponse(409, String.format("User with ID %d already exists", user.getId()));
        }
        users.put(user.getId(), user);
        return new ApiResponse(201, user);
    }

    protected Integer parseId(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
