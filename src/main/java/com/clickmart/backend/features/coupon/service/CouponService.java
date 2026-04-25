package com.clickmart.backend.features.coupon.service;

import com.clickmart.backend.entity.Coupon;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.coupon.dto.CouponDTO;
import com.clickmart.backend.features.coupon.dto.CouponValidateRequest;
import com.clickmart.backend.features.coupon.repository.CouponRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(readOnly = true)
    public List<CouponDTO> getActiveCoupons() {
        List<Coupon> coupons = couponRepository.findActiveCoupons(LocalDateTime.now());
        List<CouponDTO> result = new ArrayList<>();
        for (Coupon coupon : coupons) {
            result.add(toDTO(coupon));
        }
        return result;
    }

    public Map<String, Object> validate(CouponValidateRequest req) {
        Coupon coupon = couponRepository.findByCodeIgnoreCaseAndActiveTrue(req.getCode())
                .orElseThrow(() -> new BadRequestException("Invalid or expired coupon code"));

        if (coupon.getExpiresAt() != null && coupon.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new BadRequestException("This coupon has expired");

        if (coupon.getMinOrderValue() != null && req.getOrderTotal() < coupon.getMinOrderValue())
            throw new BadRequestException("Minimum order value for this coupon is Rs." + coupon.getMinOrderValue());

        double discount;
        if ("PERCENT".equalsIgnoreCase(coupon.getDiscountType())) {
            discount = req.getOrderTotal() * coupon.getDiscountValue() / 100.0;
            if (coupon.getMaxDiscount() != null)
                discount = Math.min(discount, coupon.getMaxDiscount());
        } else {
            discount = coupon.getDiscountValue();
        }

        discount = Math.min(discount, req.getOrderTotal());

        return Map.of(
                "valid", true,
                "discount", discount,
                "couponCode", coupon.getCode(),
                "description", coupon.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public List<CouponDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDTO> result = new ArrayList<>();
        for (Coupon coupon : coupons) {
            result.add(toDTO(coupon));
        }
        return result;
    }

    public CouponDTO create(CouponDTO req) {
        if (couponRepository.existsByCodeIgnoreCase(req.getCode()))
            throw new BadRequestException("A coupon with this code already exists");
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(req, coupon, "id");
        return toDTO(couponRepository.save(coupon));
    }

    public CouponDTO update(Long id, CouponDTO req) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
        if (!coupon.getCode().equalsIgnoreCase(req.getCode())
                && couponRepository.existsByCodeIgnoreCase(req.getCode()))
            throw new BadRequestException("A coupon with this code already exists");
        BeanUtils.copyProperties(req, coupon, "id", "createdAt");
        return toDTO(couponRepository.save(coupon));
    }

    public void delete(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
        coupon.setActive(false);
        couponRepository.save(coupon);
    }

    public CouponDTO toDTO(Coupon coupon) {
        CouponDTO dto = new CouponDTO();
        BeanUtils.copyProperties(coupon, dto);
        return dto;
    }
}
