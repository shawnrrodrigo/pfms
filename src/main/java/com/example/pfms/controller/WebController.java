package com.example.pfms.controller;

import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.service.ExpenseServiceImpl;
import com.example.pfms.service.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    @Autowired
    private IncomeServiceImpl incomeService;

    @Autowired
    private ExpenseServiceImpl expenseService;

//    @GetMapping("/incomes")
//    public String showIncomes(Model model) {
//        model.addAttribute("incomes", incomeService.getAllIncomes());
//        return "incomes";
//    }

    @GetMapping("/incomes/new")
    public String showIncomeForm(Model model) {
        model.addAttribute("income", new Income());
        return "income_form";
    }

    @PostMapping("/incomes")
    public String saveIncome(Income income) {
        incomeService.saveIncome(income);
        return "redirect:/incomes";
    }

//    @GetMapping("/expenses")
//    public String showExpenses(Model model) {
//        model.addAttribute("expenses", expenseService.getAllExpenses());
//        return "expenses";
//    }

    @GetMapping("/expenses/new")
    public String showExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense_form";
    }

    @PostMapping("/expenses")
    public String saveExpense(Expense expense) {
        expenseService.saveExpense(expense);
        return "redirect:/expenses";
    }
}
