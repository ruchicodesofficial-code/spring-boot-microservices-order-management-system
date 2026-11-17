package com.ruchicodes.orderservice.service;

import com.ruchicodes.orderservice.dto.request.OrderItemRequest;
import com.ruchicodes.orderservice.dto.request.OrderRequest;
import com.ruchicodes.orderservice.dto.response.OrderItemResponse;
import com.ruchicodes.orderservice.dto.response.OrderResponse;
import com.ruchicodes.orderservice.entity.Order;
import com.ruchicodes.orderservice.entity.OrderItem;
import com.ruchicodes.orderservice.enums.OrderStatus;
import com.ruchicodes.orderservice.exception.OrderNotFoundException;
import com.ruchicodes.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request){
        Order order = new Order();
        order.setUserId(request.userId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest:request.items()){
            OrderItem item = new OrderItem();
            item.setProductId(itemRequest.productId());
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(itemRequest.unitPrice());
            BigDecimal lineTotal = itemRequest.unitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            item.setLineTotal(lineTotal);
            order.addItem(item);

            totalAmount=totalAmount.add(lineTotal);
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(()->
                        new OrderNotFoundException("Order not found with ID: "+id));
        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id,OrderStatus status){
        Order order = orderRepository.findById(id)
                .orElseThrow(()->
                        new OrderNotFoundException("Order not found with ID: "+id));
        order.setStatus(status);
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order){
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getLineTotal()
                )).toList();
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                itemResponses,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
