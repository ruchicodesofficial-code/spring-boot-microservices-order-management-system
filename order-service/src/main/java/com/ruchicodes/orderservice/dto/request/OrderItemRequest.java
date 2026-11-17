package com.ruchicodes.orderservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
        @NotNull(message = "Product ID is required")
        @Positive(message = "Product Id must be positive")
        Long productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.01",message = "Unit price must be greater than zero")
        @Digits(integer = 10,
        fraction = 2,
        message = "Unit price must have up to 10 integer digits and 2 decimal places")
        BigDecimal unitPrice

) {
}
