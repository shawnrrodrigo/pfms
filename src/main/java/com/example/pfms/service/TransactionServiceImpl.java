package com.example.pfms.service;

import com.example.pfms.model.Budget;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.repository.BudgetRepository;
import com.example.pfms.repository.ExpenseRepository;
import com.example.pfms.repository.IncomeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    @Transactional
    public void executeTransaction(Runnable action) {
        action.run();
    }
}
