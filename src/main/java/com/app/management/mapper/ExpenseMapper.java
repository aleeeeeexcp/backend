package com.app.management.mapper;

import com.app.management.bean.ExpenseDto;
import com.app.management.model.Expense;

public class ExpenseMapper {
    
    private ExpenseMapper() {
    }

    public static final ExpenseDto toExpenseDto(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .categoryId(expense.getCategoryId() != null ? expense.getCategoryId() : null)
                .userId(expense.getUserId() != null ? expense.getUserId() : null)
                .build();
    }

    public static final Expense toExpense(ExpenseDto expenseDto) {
        return Expense.builder()
                .id(expenseDto.getId())
                .amount(expenseDto.getAmount())
                .description(expenseDto.getDescription())
                .date(expenseDto.getDate())
                .userId(expenseDto.getUserId() != null ? expenseDto.getUserId() : null)
                .categoryId(expenseDto.getCategoryId() != null ? expenseDto.getCategoryId() : null)
                .build();
    }
}
