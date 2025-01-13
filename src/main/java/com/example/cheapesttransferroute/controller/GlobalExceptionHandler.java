package com.example.cheapesttransferroute.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {



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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        String errorMessage = ex.getMessage();

        String errorKey = determineErrorKey(errorMessage);
        errorResponse.put(errorKey, errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

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