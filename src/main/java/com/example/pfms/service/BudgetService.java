package com.example.pfms.service;

import com.example.pfms.model.Budget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BudgetService {
    Budget saveBudget(Budget budget);
    List<Budget> getAllBudgets();
    Budget findById(Long id);
    void deleteBudget(Long id);
}
