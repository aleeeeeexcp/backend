package com.app.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.management.model.Income;
import com.app.management.repository.IncomeRepository;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @SuppressWarnings("null")
    public Income createIncome(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> getAllUsersIncomes(String userId) {
        return incomeRepository.findByUserId(userId);
    }

    @SuppressWarnings("null")
    public void deleteIncome(String userId, String incomeId) {
        incomeRepository.deleteById(incomeId);
    }

    public List<Income> getIncomesByGroup(String groupId) {
        return incomeRepository.findByGroupId(groupId);
    }
    
}
