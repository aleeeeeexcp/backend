package com.app.management.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.management.bean.ExpenseDto;
import com.app.management.model.Expense;
import com.app.management.service.ExpenseService;
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

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private ExpenseService expenseService;

    private ExpenseDto createExpenseDto(String id, String description, double amount, String date, String userId, String categoryId) {
        return ExpenseDto.builder()
                .id(id)
                .description(description)
                .amount(amount)
                .date(date)
                .userId(userId)
                .categoryId(categoryId)
                .build();
    }

    private Expense createExpense(String id, String description, double amount, String date, String userId, String categoryId) {
        return Expense.builder()
                .id(id)
                .description(description)
                .amount(amount)
                .date(date)
                .userId(userId)
                .categoryId(categoryId)
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
    void createExpense_returnsCreatedExpenseDto() throws Exception {
        ExpenseDto expenseDto = createExpenseDto(null, "Lunch", 15.0, "2024-01-01", null, "cat1");
        Expense createdExpense = createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1");

        when(expenseService.createExpense(any(Expense.class))).thenReturn(createdExpense);

        mockMvc.perform(post("/api/expenses/createExpense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto))
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("e1"))
                .andExpect(jsonPath("$.description").value("Lunch"))
                .andExpect(jsonPath("$.amount").value(15.0))
                .andExpect(jsonPath("$.date").value("2024-01-01"))
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.categoryId").value("cat1"));
    }

    @SuppressWarnings("null")
    @Test
    void getAllUsersExpenses_returnsListOfExpenseDtos() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1"),
                createExpense("e2", "Bus", 2.5, "2024-01-02", "user1", "cat2")
        );
        when(expenseService.getAllUsersExpenses("user1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[0].description").value("Lunch"))
                .andExpect(jsonPath("$[1].id").value("e2"))
                .andExpect(jsonPath("$[1].description").value("Bus"));
    }

    @SuppressWarnings("null")
    @Test
    void getAllUsersExpensesByCategory_returnsListOfExpenseDtos() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseService.getAllUsersExpensesByCategory("user1", "cat1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/byCategory")
                        .param("categoryId", "cat1")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[0].categoryId").value("cat1"));
    }

    @SuppressWarnings("null")
    @Test
    void deleteExpense_returnsNoContent() throws Exception {
        doNothing().when(expenseService).deleteExpense("user1", "e1");

        mockMvc.perform(delete("/api/expenses/delete")
                        .param("expenseId", "e1")
                        .with(mockUser("user1")))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllUsersExpensesSortedByAmount_returnsExpensesSorted() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Dinner", 50.0, "2024-01-01", "user1", "cat1"),
                createExpense("e2", "Lunch", 15.0, "2024-01-02", "user1", "cat1")
        );
        when(expenseService.getAllUsersExpensesSortedByAmount("user1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/sortedByAmount")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[0].amount").value(50.0))
                .andExpect(jsonPath("$[1].id").value("e2"))
                .andExpect(jsonPath("$[1].amount").value(15.0));
    }

    @Test
    void getAllUsersExpensesSortedByDate_returnsExpensesSorted() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-02", "user1", "cat1"),
                createExpense("e2", "Dinner", 50.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseService.getAllUsersExpensesSortedByDate("user1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/sortedByDate")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[1].id").value("e2"));
    }

    @Test
    void getAllUsersExpensesByCategorySortedByAmount_returnsExpensesSorted() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Dinner", 50.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseService.getAllUsersExpensesByCategorySortedByAmount("user1", "cat1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/byCategory/sortedByAmount")
                        .param("categoryId", "cat1")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"))
                .andExpect(jsonPath("$[0].amount").value(50.0));
    }

    @Test
    void getAllUsersExpensesByCategorySortedByDate_returnsExpensesSorted() throws Exception {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-02", "user1", "cat1")
        );
        when(expenseService.getAllUsersExpensesByCategorySortedByDate("user1", "cat1")).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/byCategory/sortedByDate")
                        .param("categoryId", "cat1")
                        .with(mockUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("e1"));
    }
}
