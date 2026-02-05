package com.app.management.web;

import org.springframework.http.ResponseEntity;
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
    
    
}
