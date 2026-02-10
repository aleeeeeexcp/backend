package com.app.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expense {
    
    @Id
    private String id;
    private String description;
    private double amount;
    private String date;
    private String userId;

    @Nullable
    private String categoryId;

    @Nullable
    private String groupId;
    
}
