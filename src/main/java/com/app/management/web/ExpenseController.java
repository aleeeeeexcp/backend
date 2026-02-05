package com.app.management.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.ExpenseDto;
import com.app.management.mapper.ExpenseMapper;
import com.app.management.model.Expense;
import com.app.management.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/createExpense")
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Expense expense = ExpenseMapper.toExpense(expenseDto);
        expense.setUserId(userId);
        Expense createdExpense = expenseService.createExpense(expense);
        ExpenseDto createdExpenseDto = ExpenseMapper.toExpenseDto(createdExpense);
        return ResponseEntity.ok(createdExpenseDto); 
    }

}
