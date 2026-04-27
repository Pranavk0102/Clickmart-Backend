package com.clickmart.backend.features.coupon.dto;

import java.time.LocalDateTime;

public class CouponDTO {
    private Long id;
    private String code;
    private String description;
    private String discountType;
    private Double discountValue;
    private Double minOrderValue;
    private Double maxDiscount;
    private Boolean active;
    private LocalDateTime expiresAt;

    private Long categoryId;
    private String categoryName;

    public CouponDTO() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }
    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }
    public Double getMinOrderValue() { return minOrderValue; }
    public void setMinOrderValue(Double minOrderValue) { this.minOrderValue = minOrderValue; }
    public Double getMaxDiscount() { return maxDiscount; }
    public void setMaxDiscount(Double maxDiscount) { this.maxDiscount = maxDiscount; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
