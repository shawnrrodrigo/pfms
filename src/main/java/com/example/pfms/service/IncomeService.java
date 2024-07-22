package com.example.pfms.service;

import com.example.pfms.model.Income;
import com.example.pfms.request.IncomeRequestDTO;
import com.example.pfms.response.IncomeResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IncomeService {
    List<IncomeResponseDTO> getAllIncomes(Long id);
    Income getIncomeById(Long id);
    IncomeResponseDTO updateIncome(Long id, IncomeRequestDTO incomeRequestDTO);
    Income saveIncome(Income income);
    void deleteIncome(Long id);

    IncomeResponseDTO createIncome(IncomeRequestDTO incomeRequestDTO, Long userId);
}
