package com.clickmart.backend.features.product.repository;

import java.util.Optional;
import java.util.List;

import jakarta.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clickmart.backend.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);

    List<Product> findByNameIgnoreCaseAndActiveTrue(String name);

    long countByActiveTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT(:q, '%')) OR " +
           " LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.brand) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "ORDER BY CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT(:q, '%')) THEN 0 ELSE 1 END ASC")
    Page<Product> searchProducts(@Param("q") String query, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.active = true AND p.category.id = :catId AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT(:q, '%')) OR " +
           " LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.brand) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "ORDER BY CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT(:q, '%')) THEN 0 ELSE 1 END ASC")
    Page<Product> searchInCategory(@Param("q") String query, @Param("catId") Long catId, Pageable pageable);
}
