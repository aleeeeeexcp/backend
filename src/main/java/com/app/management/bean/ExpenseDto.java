package com.app.management.bean;

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
public class ExpenseDto {

    private String id;
    private String description;
    private Double amount;
    private String date;

    @Nullable
    private String categoryId;

    @Nullable
    private String userId;

    @Nullable
    private String groupId;
    
}
