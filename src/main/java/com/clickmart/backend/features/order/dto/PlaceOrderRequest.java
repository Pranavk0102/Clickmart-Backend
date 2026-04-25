package com.clickmart.backend.features.order.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PlaceOrderRequest {

    private Long addressId;

    private String shippingName;

    @Pattern(regexp = "^$|^[0-9]{10,15}$", message = "Shipping phone must be 10-15 digits")
    private String shippingPhone;

    private String shippingAddr1;
    private String shippingAddr2;
    private String shippingCity;
    private String shippingState;

    @Pattern(regexp = "^$|^[0-9]{6}$", message = "Shipping PIN must be 6 digits")
    private String shippingPin;

    @NotBlank(message = "Delivery type is required")
    private String deliveryType;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "(?i)CARD|UPI|NETBANKING|COD|RAZORPAY", message = "Payment method must be one of: CARD, UPI, NETBANKING, COD, RAZORPAY")
    private String paymentMethod;

    private String couponCode;


    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    public PlaceOrderRequest() {}

    @AssertTrue(message = "Provide either addressId or complete shipping details")
    public boolean hasValidShippingDetails() {
        if (addressId != null) {
            return true;
        }

        return hasText(shippingName)
                && hasText(shippingPhone)
                && hasText(shippingAddr1)
                && hasText(shippingCity)
                && hasText(shippingState)
                && hasText(shippingPin);
    }

    @AssertTrue(message = "Payment details are incomplete for the selected payment method")
    public boolean hasValidPaymentDetails() {
        if (!hasText(paymentMethod)) {
            return true;
        }

        return switch (paymentMethod.trim().toUpperCase()) {
            case "RAZORPAY" -> hasText(razorpayOrderId) && hasText(razorpayPaymentId) && hasText(razorpaySignature);
            case "COD" -> true;
            default -> false;
        };
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public String getShippingName() { return shippingName; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }
    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }
    public String getShippingAddr1() { return shippingAddr1; }
    public void setShippingAddr1(String shippingAddr1) { this.shippingAddr1 = shippingAddr1; }
    public String getShippingAddr2() { return shippingAddr2; }
    public void setShippingAddr2(String shippingAddr2) { this.shippingAddr2 = shippingAddr2; }
    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }
    public String getShippingState() { return shippingState; }
    public void setShippingState(String shippingState) { this.shippingState = shippingState; }
    public String getShippingPin() { return shippingPin; }
    public void setShippingPin(String shippingPin) { this.shippingPin = shippingPin; }
    public String getDeliveryType() { return deliveryType; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }
    public String getRazorpayPaymentId() { return razorpayPaymentId; }
    public void setRazorpayPaymentId(String razorpayPaymentId) { this.razorpayPaymentId = razorpayPaymentId; }
    public String getRazorpaySignature() { return razorpaySignature; }
    public void setRazorpaySignature(String razorpaySignature) { this.razorpaySignature = razorpaySignature; }
}

