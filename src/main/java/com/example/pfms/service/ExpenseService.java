package com.example.pfms.service;

import com.example.pfms.model.Expense;
import com.example.pfms.request.ExpenseRequestDTO;
import com.example.pfms.response.ExpenseResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpenseService {
    List<ExpenseResponseDTO> getAllExpenses(Long id);
    Expense getExpenseById(Long id);
    Expense saveExpense(Expense expense);
    void deleteExpense(Long id);

    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO);

    ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO, Long userId);
}
