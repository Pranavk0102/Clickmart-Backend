package com.clickmart.backend.features.order.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.order.dto.OrderDTO;
import com.clickmart.backend.features.order.dto.PlaceOrderRequest;
import com.clickmart.backend.features.order.dto.UpdateOrderStatusRequest;
import com.clickmart.backend.features.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    

    
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getMyOrders() {
        List<OrderDTO> orders = orderService.getMyOrders();
        ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, "Orders fetched", orders);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @GetMapping("/my/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderDTO>> getMyOrder(@PathVariable("orderNumber") String orderNumber) {
        OrderDTO order = orderService.getMyOrder(orderNumber);
        ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order fetched", order);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> placeOrder(@Valid @RequestBody PlaceOrderRequest req) {
        OrderDTO newOrder = orderService.placeOrder(req);
        ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order placed successfully", newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @PatchMapping("/my/{orderNumber}/cancel")
    public ResponseEntity<ApiResponse<OrderDTO>> cancelOrder(@PathVariable("orderNumber") String orderNumber) {
        OrderDTO cancelledOrder = orderService.cancelOrder(orderNumber);
        ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order cancelled", cancelledOrder);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrder(@PathVariable("orderNumber") String orderNumber) {
        OrderDTO order = orderService.getOrder(orderNumber);
        ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Order fetched", order);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderDTO>>> getAllOrders(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        
        Page<OrderDTO> ordersPage = orderService.getAllOrders(query, status, page, size);
        ApiResponse<Page<OrderDTO>> response = new ApiResponse<>(true, "Orders fetched", ordersPage);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @PatchMapping("/{orderNumber}/status")
    public ResponseEntity<ApiResponse<OrderDTO>> updateStatus(
            @PathVariable("orderNumber") String orderNumber,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderNumber, request.getStatus());
        ApiResponse<OrderDTO> response = new ApiResponse<>(true, "Status updated", updatedOrder);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
