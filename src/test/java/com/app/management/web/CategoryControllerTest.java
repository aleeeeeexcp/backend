package com.app.management.web;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.management.bean.CategoryDto;
import com.app.management.model.Category;
import com.app.management.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private CategoryService categoryService;

    private CategoryDto createCategoryDto(String id, String name, String description) {
        return CategoryDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    private Category createCategory(String id, String name, String description) {
        return Category.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    @SuppressWarnings("null")
    @Test
    void createCategory_returnsCreatedCategoryDto() throws Exception {
        CategoryDto categoryDto = createCategoryDto(null, "Food", "Food expenses");
        Category createdCategory = createCategory("c1", "Food", "Food expenses");

        when(categoryService.createCategory(any(Category.class))).thenReturn(createdCategory);

        mockMvc.perform(post("/api/categories/createCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("c1"))
                .andExpect(jsonPath("$.name").value("Food"))
                .andExpect(jsonPath("$.description").value("Food expenses"));
    }

    @Test
    void getAllCategories_returnsListOfCategoryDtos() throws Exception {
        List<Category> categories = List.of(
                createCategory("c1", "Food", "Food expenses"),
                createCategory("c2", "Transport", "Transport expenses")
        );
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("c1"))
                .andExpect(jsonPath("$[0].name").value("Food"))
                .andExpect(jsonPath("$[1].id").value("c2"))
                .andExpect(jsonPath("$[1].name").value("Transport"));
    }

    @SuppressWarnings("null")
    @Test
    void deleteCategory_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/categories/deleteCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"c1\""))
                .andExpect(status().isNoContent());
    }
}
