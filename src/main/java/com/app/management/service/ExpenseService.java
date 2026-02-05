package com.app.management.service;

import org.springframework.stereotype.Service;
import com.app.management.model.Expense;
import com.app.management.repository.ExpenseRepository;
@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @SuppressWarnings("null")
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

}
