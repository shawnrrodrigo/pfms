package com.example.pfms.controller;

import com.example.pfms.model.Budget;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.request.BudgetRequestDTO;
import com.example.pfms.response.BudgetResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import com.example.pfms.security.UserDetailsImpl;
import com.example.pfms.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public BudgetResponseDTO createBudget(@RequestBody BudgetRequestDTO budgetRequestDTO, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return budgetService.createBudget(budgetRequestDTO, userId);
    }

    @GetMapping
    public List<BudgetResponseDTO> getAllBudgets(Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return budgetService.getAllBudgets(userId);
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
    public BudgetResponseDTO getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.findById(id);
        BudgetResponseDTO budgetResponseDTO = new BudgetResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(budget.getUser().getId());
        userResponseDTO.setName(budget.getUser().getName());
        userResponseDTO.setEmail(budget.getUser().getEmail());
        BeanUtils.copyProperties(budget, budgetResponseDTO);
        budgetResponseDTO.setUserResponseDTO(userResponseDTO);
        return budgetResponseDTO;
    }

    @PutMapping("/{id}")
    public BudgetResponseDTO updateBudget(@PathVariable Long id, @RequestBody BudgetRequestDTO budgetRequestDTO, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        budgetRequestDTO.setUserId(userId);
        return budgetService.updateBudget(id, budgetRequestDTO);
    }

    @GetMapping("/{id}/spending")
    public Double getBudgetSpending(@PathVariable Long id) {
        return budgetService.getBudgetSpending(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}
