package com.ruchicodes.productservice.exception;

public class DuplicateProductSkuException extends RuntimeException{
    public DuplicateProductSkuException(String message) {
        super(message);
    }
}
