package com.ruchicodes.paymentservice.service;

import com.ruchicodes.paymentservice.dto.PaymentRequest;
import com.ruchicodes.paymentservice.dto.PaymentResponse;
import com.ruchicodes.paymentservice.entity.Payment;
import com.ruchicodes.paymentservice.enums.PaymentStatus;
import com.ruchicodes.paymentservice.exception.PaymentNotFoundException;
import com.ruchicodes.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request){
        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setUserId(request.userId());
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponse(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()->
                        new PaymentNotFoundException("Payment not found with ID: "+id));
        return mapToResponse(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId){
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private PaymentResponse mapToResponse(Payment payment){
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreateAt()
        );
    }
}
