package com.app.management.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.management.bean.IncomeDto;
import com.app.management.mapper.IncomeMapper;
import com.app.management.model.Income;
import com.app.management.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/createIncome")
    public ResponseEntity<IncomeDto> createIncome(@RequestBody IncomeDto incomeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Income income = IncomeMapper.toIncome(incomeDto);
        income.setUserId(userId);
        Income createdIncome = incomeService.createIncome(income);
        IncomeDto createdIncomeDto = IncomeMapper.toIncomeDto(createdIncome);
        return ResponseEntity.ok(createdIncomeDto);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> getAllUsersIncomes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<Income> incomes = incomeService.getAllUsersIncomes(userId);
        List<IncomeDto> incomeDtos = incomes.stream()
                                           .map(IncomeMapper::toIncomeDto)
                                           .toList();
        return ResponseEntity.ok(incomeDtos);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteIncome(@RequestParam String incomeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        incomeService.deleteIncome(userId, incomeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sortedByAmount")
    public ResponseEntity<List<IncomeDto>> getAllUsersIncomesSortedByAmount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<Income> incomes = incomeService.getAllUsersIncomesSortedByAmount(userId);
        List<IncomeDto> incomeDtos = incomes.stream()
                                           .map(IncomeMapper::toIncomeDto)
                                           .toList();
        return ResponseEntity.ok(incomeDtos);
    }

    @GetMapping("/sortedByDate")
    public ResponseEntity<List<IncomeDto>> getAllUsersIncomesSortedByDate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        List<Income> incomes = incomeService.getAllUsersIncomesSortedByDate(userId);
        List<IncomeDto> incomeDtos = incomes.stream()
                                           .map(IncomeMapper::toIncomeDto)
                                           .toList();
        return ResponseEntity.ok(incomeDtos);
    }

    
}
