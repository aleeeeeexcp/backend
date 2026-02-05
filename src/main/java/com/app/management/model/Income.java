package com.app.management.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Income {
    
    @Id
    private String id;
    private String source;
    private double amount;
    private String description;
    private String date;
    private String userId;
    private String categoryId;
    
}
