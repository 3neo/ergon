package com.jewel.ergon.jobs.controllers;

public class StandardResponse<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public StandardResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
