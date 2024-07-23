package com.example.pfms.service;

import com.example.pfms.model.*;
import com.example.pfms.repository.BudgetRepository;
import com.example.pfms.repository.ExpenseRepository;
import com.example.pfms.repository.IncomeRepository;
import com.example.pfms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialReportServiceImpl implements FinancialReportService{
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserService userService;
    @Override
    public FinancialReport generateMonthlyReport(Long userId, int month, int year) {
        User user = userService.findById(userId);
        List<Income> incomes = incomeRepository.findByUserAndMonthAndYear(user, month, year);
        List<Expense> expenses = expenseRepository.findByUserAndMonthAndYear(user, month, year);
        List<Budget> budgets = budgetRepository.findByUser(user);

        double totalIncome = incomes.stream().mapToDouble(Income::getAmount).sum();
        double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double remainingBudget = budgets.stream().mapToDouble(Budget::getLimitAmount).sum() - totalExpenses;

        FinancialReport report = new FinancialReport();
        report.setTotalIncome(totalIncome);
        report.setTotalExpenses(totalExpenses);
        report.setRemainingBudget(remainingBudget);
        report.setIncomeDetails(incomes);
        report.setExpenseDetails(expenses);
        report.setBudgetDetails(budgets);

        return report;
    }

    @Override
    public FinancialReport generateQuarterlyReport(Long userId, int quarter, int year) {
        return null;
    }

    @Override
    public FinancialReport generateYearlyReport(Long userId, int year) {
        return null;
    }
}
