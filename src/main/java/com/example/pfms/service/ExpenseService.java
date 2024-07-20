package com.example.pfms.service;

import com.example.pfms.model.Expense;
import com.example.pfms.request.ExpenseRequestDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExpenseService {
    List<Expense> getAllExpenses(Long id);
    Expense getExpenseById(Long id);
    Expense saveExpense(Expense expense);
    void deleteExpense(Long id);

    Expense updateIncome(Long id, ExpenseRequestDTO expenseRequestDTO);
}
