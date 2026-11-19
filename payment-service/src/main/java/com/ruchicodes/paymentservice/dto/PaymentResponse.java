package com.ruchicodes.paymentservice.dto;

import com.ruchicodes.paymentservice.enums.PaymentMethod;
import com.ruchicodes.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long orderId,
        Long userId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        LocalDateTime createdAt
) {
}
