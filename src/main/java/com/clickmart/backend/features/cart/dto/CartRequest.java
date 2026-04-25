package com.clickmart.backend.features.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull @Min(1)
    private Integer quantity = 1;

    public CartRequest() {}
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
