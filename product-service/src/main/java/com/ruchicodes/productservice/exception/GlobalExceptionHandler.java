package com.ruchicodes.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String ,String>> handleProductNotFound(ProductNotFoundException exception){
        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateProductSkuException.class)
    public ResponseEntity<Map<String ,String>> handleDuplicateProductSku(DuplicateProductSkuException exception){
        Map<String,String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String >> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String,String> errors = new HashMap<>();
      exception.getBindingResult().getFieldErrors().forEach(fieldError ->
              errors.putIfAbsent(
                      fieldError.getField(),
                      fieldError.getDefaultMessage()
              ));
        return ResponseEntity.badRequest().body(errors);
    }
}

