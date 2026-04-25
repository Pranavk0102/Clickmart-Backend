package com.clickmart.backend.features.category.service;

import com.clickmart.backend.exceptions.ResourceNotFoundException;

import com.clickmart.backend.entity.Category;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.features.category.dto.CategoryDTO;
import com.clickmart.backend.features.category.dto.CategoryRequest;
import com.clickmart.backend.features.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllActive() {
        List<Category> categories = categoryRepository.findByActiveTrue();
        List<CategoryDTO> result = new ArrayList<>();
        for (Category cat : categories) {
            result.add(toDTO(cat));
        }
        return result;
    }

    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> result = new ArrayList<>();
        for (Category cat : categories) {
            result.add(toDTO(cat));
        }
        return result;
    }

    public CategoryDTO getById(Long id) {
        return toDTO(findById(id));
    }

    public CategoryDTO create(CategoryRequest req) {
        if (categoryRepository.existsByNameIgnoreCase(req.getName()))
            throw new BadRequestException("Category name already exists");
        Category cat = new Category();
        applyRequest(req, cat);
        return toDTO(categoryRepository.save(cat));
    }

    public CategoryDTO update(Long id, CategoryRequest req) {
        Category cat = findById(id);
        if (!cat.getName().equalsIgnoreCase(req.getName())
                && categoryRepository.existsByNameIgnoreCase(req.getName()))
            throw new BadRequestException("Category name already exists");
        applyRequest(req, cat);
        return toDTO(categoryRepository.save(cat));
    }

    public void delete(Long id) {
        Category cat = findById(id);
        if (categoryRepository.hasActiveProducts(id)) {
            throw new BadRequestException("Cannot delete category: it still has active products. Please delete or deactivate all products in this category first.");
        }
        cat.setActive(false);
        categoryRepository.save(cat);
    }

    public CategoryDTO toggleActive(Long id) {
        Category cat = findById(id);
        cat.setActive(!Boolean.TRUE.equals(cat.getActive()));
        return toDTO(categoryRepository.save(cat));
    }

    private Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private void applyRequest(CategoryRequest req, Category cat) {
        BeanUtils.copyProperties(req, cat);
    }

    public CategoryDTO toDTO(Category cat) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(cat, dto);
        return dto;
    }
}
