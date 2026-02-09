package com.app.management.service;

import java.util.List;

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

    public List<Expense> getAllUsersExpenses(String userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getAllUsersExpensesByCategory(String userId, String categoryId) {
        return expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
    }

    @SuppressWarnings("null")
    public void deleteExpense(String userId,String expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    public List<Expense> getAllUsersExpensesSortedByAmount(String userId) {
        return expenseRepository.findByUserIdOrderByAmountDesc(userId);
    }

    public List<Expense> getAllUsersExpensesSortedByDate(String userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId);
    }

    public List<Expense> getAllUsersExpensesByCategorySortedByAmount(String userId, String categoryId) {
        return expenseRepository.findByUserIdAndCategoryIdOrderByAmountDesc(userId, categoryId);
    }

    public List<Expense> getAllUsersExpensesByCategorySortedByDate(String userId, String categoryId) {
        return expenseRepository.findByUserIdAndCategoryIdOrderByDateDesc(userId, categoryId);
    }

}
