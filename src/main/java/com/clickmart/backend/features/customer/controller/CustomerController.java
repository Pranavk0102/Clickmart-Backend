package com.clickmart.backend.features.customer.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.customer.dto.AdminUpdateCustomerRequest;
import com.clickmart.backend.features.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllCustomers(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Customers fetched",
                customerService.getAllCustomers(query, page, size)));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Customer fetched",
                customerService.getCustomer(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateCustomer(
            @PathVariable("id") Long id,
            @Valid @RequestBody AdminUpdateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Customer updated",
                customerService.updateCustomer(id, request)));
    }

    
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ApiResponse<UserDTO>> toggleActive(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Customer status updated",
                customerService.toggleActive(id)));
    }
}
