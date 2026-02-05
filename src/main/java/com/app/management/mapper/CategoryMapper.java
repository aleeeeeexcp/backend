package com.app.management.mapper;

import com.app.management.bean.CategoryDto;
import com.app.management.model.Category;
public class CategoryMapper {
    
    private CategoryMapper() {
    }

    public static final CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static final Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
    }
    
    
}