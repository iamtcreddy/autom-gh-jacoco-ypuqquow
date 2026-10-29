package com.apiservice.handlers;

public class ApiResponse {
    private final int statusCode;
    private final Object body;

    public ApiResponse(int statusCode, Object body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getBody() {
        return body;
    }
}
