package com.clickmart.backend.features.product.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.product.dto.*;
import com.clickmart.backend.features.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "sort", defaultValue = "relevance") String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size) {

        Page<ProductDTO> products = productService.getAllProducts(query, categoryId, sort, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Products fetched", products));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Product fetched", productService.getProductById(id)));
    }

    
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<Page<ReviewDTO>>> getReviews(
            @PathVariable("id") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Reviews fetched",
                productService.getProductReviews(id, page, size)));
    }

    
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewDTO>> addReview(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReviewRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Review submitted", productService.addReview(id, req)));
    }

    
    @DeleteMapping("/{id}/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable("id") Long id, @PathVariable("reviewId") Long reviewId) {
        productService.deleteReview(id, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Review deleted", null));
    }

    

    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(
            @Valid @ModelAttribute ProductRequest req,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Product created", productService.createProduct(req, imageFile)));
    }

    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute ProductRequest req,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Product updated", productService.updateProduct(id, req, imageFile)));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Product deleted", null));
    }
}
