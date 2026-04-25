package com.clickmart.backend.features.payment.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.payment.dto.PaymentDetailsDTO;
import com.clickmart.backend.features.payment.dto.RazorpayOrderRequest;
import com.clickmart.backend.features.payment.dto.RazorpayOrderResponseDTO;
import com.clickmart.backend.features.payment.service.PaymentService;
import com.clickmart.backend.features.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public PaymentController(PaymentService paymentService,
                               OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/payments/razorpay/order")
    public ResponseEntity<ApiResponse<RazorpayOrderResponseDTO>> createRazorpayOrder(
            @Valid @RequestBody RazorpayOrderRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Razorpay order created",
                orderService.createRazorpayOrder(req)));
    }

    @GetMapping("/payments/order/{orderNumber}")
    public ResponseEntity<ApiResponse<PaymentDetailsDTO>> getPaymentForOrder(
            @PathVariable("orderNumber") String orderNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Payment details fetched",
                paymentService.getPaymentForCurrentUserOrder(orderNumber)));
    }

    @GetMapping("/admin/payments/order/{orderNumber}")
    public ResponseEntity<ApiResponse<PaymentDetailsDTO>> getPaymentForAdmin(
            @PathVariable("orderNumber") String orderNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Payment details fetched",
                paymentService.getPaymentForOrder(orderNumber)));
    }
}
