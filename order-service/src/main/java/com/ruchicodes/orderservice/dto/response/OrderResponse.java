package com.ruchicodes.orderservice.dto.response;

import com.ruchicodes.orderservice.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        List<OrderItemResponse> items,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
