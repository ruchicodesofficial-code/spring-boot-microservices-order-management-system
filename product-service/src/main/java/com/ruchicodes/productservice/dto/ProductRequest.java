package com.ruchicodes.productservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 150,message = "Product name cannot exceed 150 characters")
        String name,

        @NotBlank(message = "SKU is required")
        @Size(max = 50,message = "SKU cannot exceed 50 characters")
        String sku,

        @NotBlank(message = "Description is required")
        @Size(max = 1000,message = "Description cannot exceed 1000 characters")
        String description,

        @NotNull(message = "price is required")
        @DecimalMin(value="0.01",message = "Price must be grater than zero")
        @Digits(integer = 10,fraction = 2,message = "Price must have up to 10 integer digits and 2 decimal places")
        BigDecimal price,

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity cannot be negative")
        Integer stockQuantity
) {
}
