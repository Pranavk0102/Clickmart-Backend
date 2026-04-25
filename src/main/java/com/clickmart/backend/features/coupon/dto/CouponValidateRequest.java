package com.clickmart.backend.features.coupon.dto;

import jakarta.validation.constraints.*;

public class CouponValidateRequest {
    @NotBlank(message = "Coupon code is required")
    private String code;

    @NotNull(message = "Order total is required")
    @DecimalMin(value = "0.0")
    private Double orderTotal;

    public CouponValidateRequest() {}
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Double getOrderTotal() { return orderTotal; }
    public void setOrderTotal(Double orderTotal) { this.orderTotal = orderTotal; }
}
