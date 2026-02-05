package com.app.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.app.management.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
