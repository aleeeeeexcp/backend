package com.app.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.app.management.model.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    
    List<Expense> findByUserId(String userId);
    List<Expense> findByUserIdAndCategoryId(String userId, String categoryId);
    
}
