package com.clickmart.backend.features.payment.dto;

public class RazorpayOrderRequest {
    private String deliveryType;
    private String couponCode;

    public RazorpayOrderRequest() {}

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
