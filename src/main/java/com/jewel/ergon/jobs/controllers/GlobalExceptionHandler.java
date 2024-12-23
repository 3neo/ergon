package com.jewel.ergon.jobs.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid input: " + ex.getMessage());
    }
}
