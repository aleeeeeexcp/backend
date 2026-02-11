package com.app.management.service;

import com.app.management.model.Income;
import com.app.management.repository.IncomeRepository;
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
class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

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

    @SuppressWarnings("null")
    @Test
    void createIncome_savesAndReturnsIncome() {
        Income income = createIncome(null, "Job", 1000.0, "Salary", "2024-01-01", "user1", null);
        Income savedIncome = createIncome("i1", "Job", 1000.0, "Salary", "2024-01-01", "user1", null);
        when(incomeRepository.save(any(Income.class))).thenReturn(savedIncome);

        Income result = incomeService.createIncome(income);

        assertNotNull(result);
        assertEquals("i1", result.getId());
        assertEquals("Job", result.getSource());
        assertEquals(1000.0, result.getAmount());
        assertEquals("Salary", result.getDescription());
        assertEquals("2024-01-01", result.getDate());
        assertEquals("user1", result.getUserId());
    }

    @Test
    void getAllUsersIncomes_returnsListOfIncomes() {
        List<Income> incomes = List.of(
                createIncome("i1", "Job", 1000.0, "Salary", "2024-01-01", "user1", null),
                createIncome("i2", "Gift", 200.0, "Birthday", "2024-01-02", "user1", null)
        );
        when(incomeRepository.findByUserId("user1")).thenReturn(incomes);

        List<Income> result = incomeService.getAllUsersIncomes("user1");

        assertEquals(2, result.size());
        assertEquals("i1", result.get(0).getId());
        assertEquals("i2", result.get(1).getId());
    }

    @Test
    void deleteIncome_deletesById() {
        doNothing().when(incomeRepository).deleteById("i1");
        incomeService.deleteIncome("user1", "i1");
        verify(incomeRepository, times(1)).deleteById("i1");
    }

    @Test
    void getIncomesByGroup_returnsGroupIncomes() {
        List<Income> incomes = List.of(
                createIncome("i1", "Work", 1000.0, "Salary", "2024-01-01", "user1", null),
                createIncome("i2", "Work", 200.0, "Bonus", "2024-01-02", "user2", null)
        );
        when(incomeRepository.findByGroupId("g1")).thenReturn(incomes);

        List<Income> result = incomeService.getIncomesByGroup("g1");

        assertEquals(2, result.size());
        assertEquals("i1", result.get(0).getId());
        assertEquals("i2", result.get(1).getId());
    }
}
