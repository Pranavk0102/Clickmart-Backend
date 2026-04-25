package com.clickmart.backend.features.product.service;

import com.clickmart.backend.exceptions.ResourceNotFoundException;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.Category;
import com.clickmart.backend.entity.Product;
import java.util.List;
import com.clickmart.backend.entity.Review;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.features.category.repository.CategoryRepository;
import com.clickmart.backend.features.product.dto.*;
import com.clickmart.backend.features.product.repository.ProductRepository;
import com.clickmart.backend.features.product.repository.ReviewRepository;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final SecurityUtils securityUtils;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                               ReviewRepository reviewRepository,
                               CategoryRepository categoryRepository,
                               SecurityUtils securityUtils,
                               CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.securityUtils = securityUtils;
        this.cloudinaryService = cloudinaryService;
    }

    

    public Page<ProductDTO> getAllProducts(String query, Long categoryId, String sort, int page, int size) {
        Pageable pageable = buildPageable(sort, page, size);
        Page<Product> products;

        if (query != null && !query.isBlank()) {
            if (categoryId != null) {
                products = productRepository.searchInCategory(query, categoryId, pageable);
            } else {
                products = productRepository.searchProducts(query, pageable);
            }
        } else {
            if (categoryId != null) {
                products = productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
            } else {
                products = productRepository.findByActiveTrue(pageable);
            }
        }
        return products.map(product -> this.toDTO(product));
    }

    public ProductDTO getProductById(Long id) {
        return toDTO(findActiveProduct(id));
    }

    

    @Transactional(readOnly = true)
    public Page<ReviewDTO> getProductReviews(Long productId, int page, int size) {
        findActiveProduct(productId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewRepository.findByProductId(productId, pageable).map(review -> this.toReviewDTO(review));
    }

    public ReviewDTO addReview(Long productId, ReviewRequest req) {
        User user = securityUtils.getCurrentUser();
        Product product = findActiveProduct(productId);

        if (reviewRepository.existsByProductIdAndUserId(productId, user.getId())) {
            
            Review existing = reviewRepository.findByProductIdAndUserId(productId, user.getId()).get();
            existing.setRating(req.getRating());
            existing.setComment(req.getComment());
            Review saved = reviewRepository.save(existing);
            updateProductRating(product);
            return toReviewDTO(saved);
        }

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        Review saved = reviewRepository.save(review);
        updateProductRating(product);
        return toReviewDTO(saved);
    }

    public void deleteReview(Long productId, Long reviewId) {
        User user = securityUtils.getCurrentUser();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        if (!review.getProduct().getId().equals(productId) || !review.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Review not found or not authorized");
        }
        reviewRepository.delete(review);
        updateProductRating(findActiveProduct(productId));
    }

    

    public ProductDTO createProduct(ProductRequest req, MultipartFile imageFile) {
        if (isDuplicateProduct(req, null)) {
            throw new BadRequestException("A product with this name, brand, and category already exists");
        }
        Product product = new Product();
        applyRequest(req, product, imageFile);
        return toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductRequest req, MultipartFile imageFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (isDuplicateProduct(req, id)) {
            throw new BadRequestException("A product with this name, brand, and category already exists");
        }
        applyRequest(req, product, imageFile);
        return toDTO(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }

    private boolean isDuplicateProduct(ProductRequest req, Long excludeId) {
        List<Product> existing = productRepository.findByNameIgnoreCaseAndActiveTrue(req.getName());
        for (Product p : existing) {
            if (excludeId != null && p.getId().equals(excludeId)) {
                continue;
            }
            
            boolean sameBrand = false;
            if (p.getBrand() == null && req.getBrand() == null) {
                sameBrand = true;
            } else if (p.getBrand() != null && req.getBrand() != null && p.getBrand().equalsIgnoreCase(req.getBrand())) {
                sameBrand = true;
            }
            
            boolean sameCategory = false;
            if (p.getCategory() == null && req.getCategoryId() == null) {
                sameCategory = true;
            } else if (p.getCategory() != null && req.getCategoryId() != null && p.getCategory().getId().equals(req.getCategoryId())) {
                sameCategory = true;
            }
            
            if (sameBrand && sameCategory) {
                return true;
            }
        }
        return false;
    }

    

    private void applyRequest(ProductRequest req, Product product, MultipartFile imageFile) {
        BeanUtils.copyProperties(req, product);
        if (req.getStock() != null) {
            product.setStock(req.getStock());
        } else {
            product.setStock(0);
        }
        if (req.getCategoryId() != null) {
            Category cat = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(cat);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                if (product.getImagePublicId() != null) {
                    cloudinaryService.deleteImage(product.getImagePublicId());
                }
                String uploadedUrl = cloudinaryService.uploadImage(imageFile);
                product.setImageUrl(uploadedUrl);
                product.setImagePublicId(cloudinaryService.getPublicIdFromUrl(uploadedUrl));
            } catch (Exception e) {
                throw new BadRequestException("Failed to upload product image: " + e.getMessage());
            }
        } else if (req.getImageUrl() != null && !req.getImageUrl().isBlank()) {
            product.setImageUrl(req.getImageUrl().trim());
        }
    }

    private void updateProductRating(Product product) {
        Double avg = reviewRepository.findAvgRatingByProductId(product.getId());
        Long count = reviewRepository.countByProductId(product.getId());
        if (avg != null) {
            product.setRating(Math.round(avg * 10.0) / 10.0);
        } else {
            product.setRating(0.0);
        }

        if (count != null) {
            product.setReviewCount(count.intValue());
        } else {
            product.setReviewCount(0);
        }
        productRepository.save(product);
    }

    private Product findActiveProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!Boolean.TRUE.equals(p.getActive())) {
            throw new ResourceNotFoundException("Product not found");
        }
        return p;
    }

    private Pageable buildPageable(String sort, int page, int size) {
        Sort s;
        if (sort == null) {
            s = Sort.by("createdAt").descending();
        } else if ("price-asc".equals(sort)) {
            s = Sort.by("price").ascending();
        } else if ("price-desc".equals(sort)) {
            s = Sort.by("price").descending();
        } else if ("rating".equals(sort)) {
            s = Sort.by("rating").descending();
        } else if ("discount".equals(sort)) {
            s = Sort.by("originalPrice").descending();
        } else {
            s = Sort.by("createdAt").descending();
        }
        return PageRequest.of(page, size, s);
    }

    public ProductDTO toDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(p, dto);
        if (p.getCategory() != null) {
            dto.setCategoryId(p.getCategory().getId());
            dto.setCategoryName(p.getCategory().getName());
        }
        return dto;
    }

    private ReviewDTO toReviewDTO(Review r) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(r.getId());
        dto.setProductId(r.getProduct().getId());
        dto.setUserId(r.getUser().getId());
        dto.setUserName(r.getUser().getFirstName() + " " + r.getUser().getLastName());
        dto.setRating(r.getRating());
        dto.setComment(r.getComment());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}
