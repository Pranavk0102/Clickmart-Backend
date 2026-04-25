package com.clickmart.backend.features.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.inventory.dto.InventoryStockUpdateRequest;
import com.clickmart.backend.features.inventory.service.InventoryService;
import com.clickmart.backend.features.product.dto.ProductDTO;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getInventory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Inventory fetched",
                inventoryService.getInventory(page, size)));
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<ProductDTO>> updateStock(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody InventoryStockUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Stock updated",
                inventoryService.updateStock(productId, request.getStock())));
    }
}
