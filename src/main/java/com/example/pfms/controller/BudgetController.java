package com.example.pfms.controller;

import com.example.pfms.model.Budget;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.service.BudgetServiceImpl;
import com.example.pfms.service.ExpenseServiceImpl;
import com.example.pfms.service.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetServiceImpl budgetService;

    @Autowired
    private IncomeServiceImpl incomeService;

    @Autowired
    private ExpenseServiceImpl expenseService;

    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.saveBudget(budget);
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

//    @GetMapping("/reports")
//    public String generateReport(Model model) {
//        List<Income> incomes = incomeService.getAllIncomes();
//        List<Expense> expenses = expenseService.getAllExpenses();
//
//        // Add logic to calculate and generate reports
//
//        model.addAttribute("incomes", incomes);
//        model.addAttribute("expenses", expenses);
//        return "report";
//    }


    @GetMapping("/{id}")
    public Budget getBudgetById(@PathVariable Long id) {
        return budgetService.findById(id);
    }

    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        Budget existingBudget = budgetService.findById(id);
        existingBudget.setCategory(budget.getCategory());
        existingBudget.setLimit(budget.getLimit());
        existingBudget.setStartDate(budget.getStartDate());
        existingBudget.setEndDate(budget.getEndDate());
        return budgetService.saveBudget(existingBudget);
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}
