package com.app.management.service;

import org.springframework.stereotype.Service;

import com.app.management.model.Category;
import com.app.management.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @SuppressWarnings("null")
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
