package com.clickmart.backend.features.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.delivery.dto.DeliveryOptionDTO;
import com.clickmart.backend.features.delivery.dto.DeliveryOptionRequest;
import com.clickmart.backend.features.delivery.service.DeliveryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/options")
    public ResponseEntity<ApiResponse<List<DeliveryOptionDTO>>> getOptions() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Delivery options fetched", deliveryService.getActiveOptions()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeliveryOptionDTO>>> getAllOptions() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "All delivery options fetched", deliveryService.getAllOptions()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeliveryOptionDTO>> create(
            @Valid @RequestBody DeliveryOptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Delivery option created", deliveryService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeliveryOptionDTO>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody DeliveryOptionRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Delivery option updated", deliveryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        deliveryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Delivery option deleted", null));
    }
}
