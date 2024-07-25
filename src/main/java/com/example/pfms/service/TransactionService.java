package com.example.pfms.service;

import com.example.pfms.model.Budget;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import org.springframework.stereotype.Component;

@Component
public interface TransactionService {
    void executeTransaction(Runnable action);
}
