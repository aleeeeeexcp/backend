package com.app.management.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.management.bean.CategoryDto;
import com.app.management.model.Category;
import com.app.management.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(new CategoryDto(createdCategory.getId(), createdCategory.getName(), createdCategory.getDescription()));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> new CategoryDto(category.getId(), category.getName(), category.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }
    
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Void> deleteCategory(@RequestBody String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
