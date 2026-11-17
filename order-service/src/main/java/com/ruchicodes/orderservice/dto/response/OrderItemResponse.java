package com.ruchicodes.orderservice.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(

        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
