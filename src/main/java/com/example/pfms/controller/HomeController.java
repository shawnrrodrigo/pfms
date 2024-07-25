package com.example.pfms.controller;

import com.example.pfms.model.Budget;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.service.BudgetService;
import com.example.pfms.service.ExpenseService;
import com.example.pfms.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private BudgetService budgetService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

//    @GetMapping("/incomes")
//    public String incomes(Model model) {
//        List<Income> incomes = incomeService.findAll();
//        model.addAttribute("incomes", incomes);
//        return "incomes";
//    }
//
//    @GetMapping("/expenses")
//    public String expenses(Model model) {
//        List<Expense> expenses = expenseService.findAll();
//        model.addAttribute("expenses", expenses);
//        return "expenses";
//    }
//
//    @GetMapping("/budgets")
//    public String budgets(Model model) {
//        List<Budget> budgets = budgetService.findAll();
//        model.addAttribute("budgets", budgets);
//        return "budgets";
//    }
//
//    @GetMapping("/reports")
//    public String reports(Model model) {
//        model.addAttribute("monthlyReportData", reportService.getMonthlyReportData());
//        model.addAttribute("quarterlyReportData", reportService.getQuarterlyReportData());
//        model.addAttribute("yearlyReportData", reportService.getYearlyReportData());
//        return "reports";
//    }
}
