package com.example.pfms.model;

import lombok.Data;

import java.util.List;

@Data
public class FinancialReport {
    private double totalIncome;
    private double totalExpenses;
    private double remainingBudget;
    private List<Income> incomeDetails;
    private List<Expense> expenseDetails;
    private List<Budget> budgetDetails;
}
