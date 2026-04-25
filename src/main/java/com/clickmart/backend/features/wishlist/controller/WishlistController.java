package com.clickmart.backend.features.wishlist.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.product.dto.ProductDTO;
import com.clickmart.backend.features.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getWishlist() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Wishlist fetched", wishlistService.getWishlist()));
    }

    
    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> addToWishlist(@PathVariable("productId") Long productId) {
        wishlistService.addToWishlist(productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Added to wishlist", null));
    }

    
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable("productId") Long productId) {
        wishlistService.removeFromWishlist(productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Removed from wishlist", null));
    }
}
