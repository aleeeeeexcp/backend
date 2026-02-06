package com.app.management.service;

import java.util.List;

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

    @SuppressWarnings("null")
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    

}
