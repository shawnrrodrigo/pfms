package com.example.pfms.service;

import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.repository.ExpenseRepository;
import com.example.pfms.request.ExpenseRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(Long id){
        return expenseRepository.findByUserId(id);
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id).orElse(null);
    }

    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id){
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense updateIncome(Long id, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = getExpenseById(id);
        if(Objects.equals(expense.getUser().getId(), expenseRequestDTO.getUserId())){
            BeanUtils.copyProperties(expenseRequestDTO, expense);
            return expenseRepository.save(expense);
        }
        return null;
    }
}
