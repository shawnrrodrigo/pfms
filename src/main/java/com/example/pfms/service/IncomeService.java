package com.example.pfms.service;

import com.example.pfms.model.Income;
import com.example.pfms.request.IncomeRequestDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IncomeService {
    List<Income> getAllIncomes(Long id);
    Income getIncomeById(Long id);
    Income updateIncome(Long id, IncomeRequestDTO incomeRequestDTO);
    Income saveIncome(Income income);
    void deleteIncome(Long id);
}
