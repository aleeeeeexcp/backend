package com.app.management.service;

import com.app.management.model.Expense;
import com.app.management.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

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

    @SuppressWarnings("null")
    @Test
    void createExpense_savesAndReturnsExpense() {
        Expense expense = createExpense(null, "Lunch", 15.0, "2024-01-01", "user1", "cat1");
        Expense savedExpense = createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1");
        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        Expense result = expenseService.createExpense(expense);

        assertNotNull(result);
        assertEquals("e1", result.getId());
        assertEquals("Lunch", result.getDescription());
        assertEquals(15.0, result.getAmount());
        assertEquals("2024-01-01", result.getDate());
        assertEquals("user1", result.getUserId());
        assertEquals("cat1", result.getCategoryId());
    }

    @Test
    void getAllUsersExpenses_returnsListOfExpenses() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1"),
                createExpense("e2", "Bus", 2.5, "2024-01-02", "user1", "cat2")
        );
        when(expenseRepository.findByUserId("user1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpenses("user1");

        assertEquals(2, result.size());
        assertEquals("e1", result.get(0).getId());
        assertEquals("e2", result.get(1).getId());
    }

    @Test
    void getAllUsersExpensesByCategory_returnsListOfExpenses() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseRepository.findByUserIdAndCategoryId("user1", "cat1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpensesByCategory("user1", "cat1");

        assertEquals(1, result.size());
        assertEquals("e1", result.get(0).getId());
        assertEquals("cat1", result.get(0).getCategoryId());
    }

    @Test
    void deleteExpense_deletesById() {
        doNothing().when(expenseRepository).deleteById("e1");
        expenseService.deleteExpense("user1", "e1");
        verify(expenseRepository, times(1)).deleteById("e1");
    }

    @Test
    void getAllUsersExpensesSortedByAmount_returnsExpensesSorted() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Dinner", 50.0, "2024-01-01", "user1", "cat1"),
                createExpense("e2", "Lunch", 15.0, "2024-01-02", "user1", "cat1")
        );
        when(expenseRepository.findByUserIdOrderByAmountDesc("user1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpensesSortedByAmount("user1");

        assertEquals(2, result.size());
        assertEquals("e1", result.get(0).getId());
        assertEquals(50.0, result.get(0).getAmount());
    }

    @Test
    void getAllUsersExpensesSortedByDate_returnsExpensesSorted() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-02", "user1", "cat1"),
                createExpense("e2", "Dinner", 50.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseRepository.findByUserIdOrderByDateDesc("user1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpensesSortedByDate("user1");

        assertEquals(2, result.size());
        assertEquals("e1", result.get(0).getId());
    }

    @Test
    void getAllUsersExpensesByCategorySortedByAmount_returnsExpensesSorted() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Dinner", 50.0, "2024-01-01", "user1", "cat1")
        );
        when(expenseRepository.findByUserIdAndCategoryIdOrderByAmountDesc("user1", "cat1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpensesByCategorySortedByAmount("user1", "cat1");

        assertEquals(1, result.size());
        assertEquals("e1", result.get(0).getId());
        assertEquals(50.0, result.get(0).getAmount());
    }

    @Test
    void getAllUsersExpensesByCategorySortedByDate_returnsExpensesSorted() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Lunch", 15.0, "2024-01-02", "user1", "cat1")
        );
        when(expenseRepository.findByUserIdAndCategoryIdOrderByDateDesc("user1", "cat1")).thenReturn(expenses);

        List<Expense> result = expenseService.getAllUsersExpensesByCategorySortedByDate("user1", "cat1");

        assertEquals(1, result.size());
        assertEquals("e1", result.get(0).getId());
    }

    @Test
    void getExpensesByGroup_returnsGroupExpenses() {
        List<Expense> expenses = List.of(
                createExpense("e1", "Groceries", 50.0, "2024-01-01", "user1", "cat1"),
                createExpense("e2", "Dinner", 30.0, "2024-01-02", "user2", "cat1")
        );
        when(expenseRepository.findByGroupId("g1")).thenReturn(expenses);

        List<Expense> result = expenseService.getExpensesByGroup("g1");

        assertEquals(2, result.size());
        assertEquals("e1", result.get(0).getId());
        assertEquals("e2", result.get(1).getId());
    }
}
