package com.clickmart.backend.features.cart.service;

import com.clickmart.backend.exceptions.ResourceNotFoundException;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.CartItem;
import com.clickmart.backend.entity.Product;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.features.cart.dto.CartItemDTO;
import com.clickmart.backend.features.cart.dto.CartRequest;
import com.clickmart.backend.features.cart.repository.CartItemRepository;
import com.clickmart.backend.features.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;

    @Autowired
    public CartService(CartItemRepository cartItemRepository,
                               ProductRepository productRepository,
                               SecurityUtils securityUtils) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.securityUtils = securityUtils;
    }

    public List<CartItemDTO> getCart() {
        Long userId = securityUtils.getCurrentUserId();
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        List<CartItemDTO> result = new ArrayList<>();
        for (CartItem item : items) {
            result.add(toDTO(item));
        }
        return result;
    }

    public CartItemDTO addItem(CartRequest req) {
        User user = securityUtils.getCurrentUser();
        Product product = productRepository.findByIdAndActiveTrue(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < req.getQuantity())
            throw new BadRequestException("Insufficient stock");

        CartItem item = cartItemRepository
                .findByUserIdAndProductId(user.getId(), product.getId())
                .orElse(new CartItem());

        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(item.getId() == null
                ? req.getQuantity()
                : item.getQuantity() + req.getQuantity());

        if (product.getStock() < item.getQuantity())
            throw new BadRequestException("Insufficient stock");

        return toDTO(cartItemRepository.save(item));
    }

    public CartItemDTO updateItem(Long itemId, CartRequest req) {
        Long userId = securityUtils.getCurrentUserId();
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getUser().getId().equals(userId))
            throw new ResourceNotFoundException("Cart item not found");

        if (!Boolean.TRUE.equals(item.getProduct().getActive()))
            throw new ResourceNotFoundException("Product not found");

        if (item.getProduct().getStock() < req.getQuantity())
            throw new BadRequestException("Insufficient stock");

        item.setQuantity(req.getQuantity());
        return toDTO(cartItemRepository.save(item));
    }

    public void removeItem(Long itemId) {
        Long userId = securityUtils.getCurrentUserId();
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (!item.getUser().getId().equals(userId))
            throw new ResourceNotFoundException("Cart item not found");
        cartItemRepository.delete(item);
    }

    public void clearCart() {
        cartItemRepository.deleteByUserId(securityUtils.getCurrentUserId());
    }

    public CartItemDTO toDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductImage(item.getProduct().getImageUrl());
        dto.setPrice(item.getProduct().getPrice());
        dto.setOriginalPrice(item.getProduct().getOriginalPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getProduct().getPrice() * item.getQuantity());
        return dto;
    }
}
