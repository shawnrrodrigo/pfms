package com.example.pfms.service;

import com.example.pfms.model.Budget;
import com.example.pfms.request.BudgetRequestDTO;
import com.example.pfms.response.BudgetResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BudgetService {
    Budget saveBudget(Budget budget);
    List<BudgetResponseDTO> getAllBudgets(Long id);
    Budget findById(Long id);
    void deleteBudget(Long id);

    BudgetResponseDTO createBudget(BudgetRequestDTO budgetRequestDTO, Long userId);

    BudgetResponseDTO updateBudget(Long id, BudgetRequestDTO budgetRequestDTO);

    Double getBudgetSpending(Long id);
}
