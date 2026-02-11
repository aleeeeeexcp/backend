package com.app.management.service;

import com.app.management.model.Category;
import com.app.management.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category createCategory(String id, String name, String description) {
        return Category.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    @SuppressWarnings("null")
    @Test
    void createCategory_savesAndReturnsCategory() {
        Category category = createCategory(null, "Food", "Food expenses");
        Category savedCategory = createCategory("c1", "Food", "Food expenses");
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        Category result = categoryService.createCategory(category);

        assertNotNull(result);
        assertEquals("c1", result.getId());
        assertEquals("Food", result.getName());
        assertEquals("Food expenses", result.getDescription());
    }

    @Test
    void getCategoryById_returnsCategoryIfExists() {
        Category category = createCategory("c1", "Food", "Food expenses");
        when(categoryRepository.findById("c1")).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById("c1");

        assertNotNull(result);
        assertEquals("c1", result.getId());
    }

    @Test
    void getCategoryById_returnsNullIfNotExists() {
        when(categoryRepository.findById("c2")).thenReturn(Optional.empty());

        Category result = categoryService.getCategoryById("c2");

        assertNull(result);
    }

    @Test
    void getAllCategories_returnsListOfCategories() {
        List<Category> categories = List.of(
                createCategory("c1", "Food", "Food expenses"),
                createCategory("c2", "Transport", "Transport expenses")
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("c1", result.get(0).getId());
        assertEquals("c2", result.get(1).getId());
    }

    @Test
    void deleteCategory_deletesById() {
        categoryService.deleteCategory("c1");
        verify(categoryRepository).deleteById("c1");
    }
}
