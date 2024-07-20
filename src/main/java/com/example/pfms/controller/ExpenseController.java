package com.example.pfms.controller;

import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.request.ExpenseRequestDTO;
import com.example.pfms.response.ExpenseResponseDTO;
import com.example.pfms.response.IncomeResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import com.example.pfms.security.UserDetailsImpl;
import com.example.pfms.service.ExpenseService;
import com.example.pfms.service.ExpenseServiceImpl;
import com.example.pfms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<ExpenseResponseDTO> getAllExpenses(Authentication authentication) {
        Long id = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        List<Expense> expenseList =  expenseService.getAllExpenses(id);
        return expenseList.stream()
                .map(expense -> {
                    ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();
                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(expenseList, expenseResponseDTO);
                    userResponseDTO.setName(expense.getUser().getName());
                    userResponseDTO.setEmail(expense.getUser().getEmail());
                    userResponseDTO.setId(expense.getUser().getId());
                    expenseResponseDTO.setUserResponseDTO(userResponseDTO);
                    return expenseResponseDTO;
                }).toList();
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(expense.getUser().getId());
        userResponseDTO.setName(expense.getUser().getName());
        userResponseDTO.setEmail(expense.getUser().getEmail());
        BeanUtils.copyProperties(expense, expenseResponseDTO);
        expenseResponseDTO.setUserResponseDTO(userResponseDTO);
        return expenseResponseDTO;
    }

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO, Authentication authentication) {
        Expense expense = new Expense();
        BeanUtils.copyProperties(expenseRequestDTO, expense);
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        User user = userService.findById(userId);
        expense.setUser(user);
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody ExpenseRequestDTO expenseRequestDTO, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        expenseRequestDTO.setUserId(userId);
        return expenseService.updateIncome(id, expenseRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
