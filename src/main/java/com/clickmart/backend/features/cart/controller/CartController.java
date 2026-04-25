package com.clickmart.backend.features.cart.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.cart.dto.CartItemDTO;
import com.clickmart.backend.features.cart.dto.CartRequest;
import com.clickmart.backend.features.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getCart() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Cart fetched", cartService.getCart()));
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<CartItemDTO>> addItem(@Valid @RequestBody CartRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Item added to cart", cartService.addItem(req)));
    }

    
    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<CartItemDTO>> updateItem(
            @PathVariable("itemId") Long itemId, @Valid @RequestBody CartRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Cart item updated", cartService.updateItem(itemId, req)));
    }

    
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(@PathVariable("itemId") Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Item removed from cart", null));
    }

    
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Cart cleared", null));
    }
}
