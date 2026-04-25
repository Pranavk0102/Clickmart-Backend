package com.clickmart.backend.features.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.entity.Product;
import com.clickmart.backend.exceptions.ResourceNotFoundException;
import com.clickmart.backend.features.product.dto.ProductDTO;
import com.clickmart.backend.features.product.repository.ProductRepository;
import com.clickmart.backend.features.product.service.ProductService;

@Service
@Transactional
public class InventoryService {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public InventoryService(ProductRepository productRepository,
                               ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getInventory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return productRepository.findAll(pageable).map(productService::toDTO);
    }

    public ProductDTO updateStock(Long productId, Integer stock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStock(stock);
        return productService.toDTO(productRepository.save(product));
    }
}
