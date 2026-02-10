package com.app.management.mapper;

import com.app.management.bean.IncomeDto;
import com.app.management.model.Income;

public class IncomeMapper {
    
    private IncomeMapper() {
    }

    public static final IncomeDto toIncomeDto(Income income) {

        return IncomeDto.builder()
                .id(income.getId())
                .source(income.getSource())
                .amount(income.getAmount())
                .description(income.getDescription())
                .date(income.getDate().toString())
                .userId(income.getUserId())
                .groupId(income.getGroupId() != null ? income.getGroupId() : null)
                .build();
                
    }

    public static final Income toIncome(IncomeDto incomeDto) {
        return Income.builder()
                .id(incomeDto.getId())
                .source(incomeDto.getSource())
                .amount(incomeDto.getAmount())
                .description(incomeDto.getDescription())
                .date(incomeDto.getDate())
                .userId(incomeDto.getUserId())
                .groupId(incomeDto.getGroupId() != null ? incomeDto.getGroupId() : null)
                .build();
    }
}
 