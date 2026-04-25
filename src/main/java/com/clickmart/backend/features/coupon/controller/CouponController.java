package com.clickmart.backend.features.coupon.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.coupon.dto.CouponDTO;
import com.clickmart.backend.features.coupon.dto.CouponValidateRequest;
import com.clickmart.backend.features.coupon.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validate(
            @Valid @RequestBody CouponValidateRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Coupon valid", couponService.validate(req)));
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CouponDTO>>> getActive() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Coupons fetched", couponService.getActiveCoupons()));
    }

    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CouponDTO>>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "All coupons fetched", couponService.getAllCoupons()));
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<CouponDTO>> create(@Valid @RequestBody CouponDTO req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Coupon created", couponService.create(req)));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CouponDTO>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CouponDTO req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Coupon updated", couponService.update(id, req)));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Long id) {
        couponService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Coupon deleted", null));
    }
}
