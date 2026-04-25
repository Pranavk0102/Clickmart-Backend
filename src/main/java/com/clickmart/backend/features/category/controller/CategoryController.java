package com.clickmart.backend.features.category.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.category.dto.CategoryDTO;
import com.clickmart.backend.features.category.dto.CategoryRequest;
import com.clickmart.backend.features.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getActiveCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Categories fetched", categoryService.getAllActive()));
    }

    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "All categories fetched", categoryService.getAll()));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Category fetched", categoryService.getById(id)));
    }

    
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Category created", categoryService.create(req)));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable("id") Long id, @Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Category updated", categoryService.update(id, req)));
    }

    
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<ApiResponse<CategoryDTO>> toggleActive(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Category status toggled", categoryService.toggleActive(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Category deleted", null));
    }
}
