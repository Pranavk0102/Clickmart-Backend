package com.clickmart.backend.features.wishlist.service;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Product;
import com.clickmart.backend.entity.WishlistItem;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.product.dto.ProductDTO;
import com.clickmart.backend.features.product.repository.ProductRepository;
import com.clickmart.backend.features.product.service.ProductService;
import com.clickmart.backend.features.wishlist.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private final ProductService productService;

    @Autowired
    public WishlistService(WishlistItemRepository wishlistItemRepository,
                               ProductRepository productRepository,
                               SecurityUtils securityUtils,
                               ProductService productService) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
        this.securityUtils = securityUtils;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getWishlist() {
        Long userId = securityUtils.getCurrentUserId();
        List<WishlistItem> items = wishlistItemRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<ProductDTO> result = new ArrayList<>();
        for (WishlistItem item : items) {
            result.add(productService.toDTO(item.getProduct()));
        }
        return result;
    }

    public void addToWishlist(Long productId) {
        Long userId = securityUtils.getCurrentUserId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!Boolean.TRUE.equals(product.getActive()))
            throw new ResourceNotFoundException("Product not found");

        if (wishlistItemRepository.existsByUserIdAndProductId(userId, productId))
            throw new BadRequestException("Product is already in your wishlist");

        WishlistItem item = new WishlistItem();
        item.setUser(securityUtils.getCurrentUser());
        item.setProduct(product);
        wishlistItemRepository.save(item);
    }

    public void removeFromWishlist(Long productId) {
        Long userId = securityUtils.getCurrentUserId();
        if (!wishlistItemRepository.existsByUserIdAndProductId(userId, productId))
            throw new ResourceNotFoundException("Product not found in wishlist");
        wishlistItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
