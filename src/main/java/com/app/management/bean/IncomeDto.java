package com.app.management.bean;

import com.mongodb.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IncomeDto {
    
    private String id;
    private String source;
    private double amount;
    private String description;
    private String date;

    @Nullable
    private String userId;
    @Nullable
    private String categoryId;
}
