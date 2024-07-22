package com.example.pfms.controller;

import com.example.pfms.model.Budget;
import com.example.pfms.service.BudgetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/budgets")
public class BudgetWebController {
    @Autowired
    private BudgetServiceImpl budgetService;

//    @GetMapping
//    public String showBudgets(Model model) {
//        model.addAttribute("budgets", budgetService.getAllBudgets());
//        return "budgets";
//    }

    @GetMapping("/new")
    public String showBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());
        return "budget_form";
    }

    @PostMapping
    public String saveBudget(Budget budget) {
        budgetService.saveBudget(budget);
        return "redirect:/budgets";
    }
}
