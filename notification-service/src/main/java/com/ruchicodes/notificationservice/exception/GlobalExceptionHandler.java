package com.ruchicodes.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleNotificationNotFoundException(NotificationNotFoundException ex){
        Map<String,String> response = new HashMap<>();
        response.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> response = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                response.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleInvalidRequestBody(HttpMessageNotReadableException ex){
        Map<String,String> response = new HashMap<>();
        response.put("message","Invalid request body or enum value");
            return ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGlobalException(Exception ex){
        Map<String,String> response = new HashMap<>();
        response.put("message","Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
