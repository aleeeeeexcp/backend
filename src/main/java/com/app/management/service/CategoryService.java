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

    @SuppressWarnings("null")
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @SuppressWarnings("null")
    public void updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            categoryRepository.save(existingCategory);
        }
    }
    

}
