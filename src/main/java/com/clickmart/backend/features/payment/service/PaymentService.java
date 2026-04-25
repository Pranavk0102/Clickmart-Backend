package com.clickmart.backend.features.payment.service;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Order;
import com.clickmart.backend.entity.Payment;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.order.repository.OrderRepository;
import com.clickmart.backend.features.payment.dto.PaymentDetailsDTO;
import com.clickmart.backend.features.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final SecurityUtils securityUtils;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                               OrderRepository orderRepository,
                               SecurityUtils securityUtils) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.securityUtils = securityUtils;
    }

    public PaymentDetailsDTO getPaymentForCurrentUserOrder(String orderNumber) {
        Long userId = securityUtils.getCurrentUserId();
        Order order = orderRepository.findByOrderNumberAndUserId(orderNumber, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toDTO(getPayment(order), order);
    }

    public PaymentDetailsDTO getPaymentForOrder(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toDTO(getPayment(order), order);
    }

    @Transactional
    public void updatePaymentStatus(Order order, String status) {
        paymentRepository.findByOrderId(order.getId()).ifPresent(payment -> payment.setStatus(status));
    }

    private Payment getPayment(Order order) {
        return paymentRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    private PaymentDetailsDTO toDTO(Payment payment, Order order) {
        PaymentDetailsDTO dto = new PaymentDetailsDTO();
        dto.setPaymentId(payment.getId());
        dto.setOrderId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setMethod(payment.getMethod());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());

        dto.setTransactionId(payment.getTransactionId());
        dto.setCreatedAt(payment.getCreatedAt());

        User user = order.getUser();
        if (user != null) {
            dto.setCustomerId(user.getId());
            dto.setCustomerEmail(user.getEmail());
            dto.setCustomerName(user.getFirstName() + " " + user.getLastName());
        }
        return dto;
    }
}
