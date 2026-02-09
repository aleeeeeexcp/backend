package com.app.management.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.management.bean.IncomeDto;
import com.app.management.model.Income;
import com.app.management.service.IncomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

import static org.mockito.Mockito.doNothing;

@WebMvcTest(IncomeController.class)
@AutoConfigureMockMvc(addFilters = false)
class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private IncomeService incomeService;

    private IncomeDto createIncomeDto(String id, String source, double amount, String description, String date, String userId, String categoryId) {
        return IncomeDto.builder()
                .id(id)
                .source(source)
                .amount(amount)
                .description(description)
                .date(date)
                .userId(userId)
                .build();
    }

    private Income createIncome(String id, String source, double amount, String description, String date, String userId, String categoryId) {
        return Income.builder()
                .id(id)
                .source(source)
                .amount(amount)
                .description(description)
                .date(date)
                .userId(userId)
                .build();
    }

    private RequestPostProcessor mockUser(String userId) {
        return request -> {
            Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
            when(authentication.getName()).thenReturn(userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return request;
        };
    }

    @SuppressWarnings("null")
    @Test
    void createIncome_returnsCreatedIncomeDto() throws Exception {
        IncomeDto incomeDto = createIncomeDto(null, "Job", 1000.0, "Salary", "2024-01-01", null, null);
        Income createdIncome = createIncome("i1", "Job", 1000.0, "Salary", "2024-01-01", "user1", null);

        when(incomeService.createIncome(any(Income.class))).thenReturn(createdIncome);

        mockMvc.perform(post("/api/incomes/createIncome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incomeDto))
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("i1"))
                .andExpect(jsonPath("$.source").value("Job"))
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.description").value("Salary"))
                .andExpect(jsonPath("$.date").value("2024-01-01"))
                .andExpect(jsonPath("$.userId").value("user1"));
    }

    @SuppressWarnings("null")
    @Test
    void getAllUsersIncomes_returnsListOfIncomeDtos() throws Exception {
        List<Income> incomes = List.of(
                createIncome("i1", "Job", 1000.0, "Salary", "2024-01-01", "user1", null),
                createIncome("i2", "Gift", 200.0, "Birthday", "2024-01-02", "user1", null)
        );
        when(incomeService.getAllUsersIncomes("user1")).thenReturn(incomes);

        mockMvc.perform(get("/api/incomes")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("i1"))
                .andExpect(jsonPath("$[0].source").value("Job"))
                .andExpect(jsonPath("$[1].id").value("i2"))
                .andExpect(jsonPath("$[1].source").value("Gift"));
    }

    @SuppressWarnings("null")
    @Test
    void deleteIncome_returnsNoContent() throws Exception {
        doNothing().when(incomeService).deleteIncome("user1", "i1");

        mockMvc.perform(delete("/api/incomes/delete")
                        .param("incomeId", "i1")
                        .with(mockUser("user1")))
                .andExpect(status().isNoContent());
    }
}
