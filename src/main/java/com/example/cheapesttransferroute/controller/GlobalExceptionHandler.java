package com.example.cheapesttransferroute.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class  for global exception handling
 * to provide consistent error responses for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions caused by unreadable HTTP messages (e.g., invalid JSON input).
     *
     * @param ex the exception thrown when the HTTP message is not readable
     * @return a response entity with error details and a BAD_REQUEST status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;
            jsonMappingException.getPath().forEach(reference -> {
                String fieldName = reference.getFieldName();
                if (fieldName != null) {
                    errorResponse.put(fieldName, "Invalid format for field: " + fieldName);
                }
            });
        } else {
            errorResponse.put("error", "Invalid input format");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles IllegalArgumentException, typically thrown for invalid method arguments.
     *
     * @param ex the exception thrown for illegal arguments
     * @return a response entity with error details and a BAD_REQUEST status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        String errorKey = determineErrorKey(errorMessage);
        errorResponse.put(errorKey, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Determines the error key based on the exception message content.
     *
     * @param errorMessage the exception message
     * @return a specific error key or a default key
     */
    private String determineErrorKey(String errorMessage) {
        if (errorMessage.contains("Maximum weight")) {
            return "maxWeight";
        } else if (errorMessage.contains("Available transfers list")) {
            return "availableTransfers";
        } else if (errorMessage.contains("Weight must be at least 1")) {
            return "weight";
        } else if (errorMessage.contains("Cost must be at least 1")) {
            return "cost";
        } else {
            return "error";
        }
    }
}