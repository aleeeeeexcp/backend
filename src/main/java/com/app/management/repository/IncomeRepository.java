package com.app.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.app.management.model.Income;

public interface IncomeRepository extends MongoRepository<Income, String> {

    List<Income> findByUserId(String userId);
    
}
