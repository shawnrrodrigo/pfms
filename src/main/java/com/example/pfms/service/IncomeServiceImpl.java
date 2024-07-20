package com.example.pfms.service;

import com.example.pfms.model.Income;
import com.example.pfms.repository.IncomeRepository;
import com.example.pfms.request.IncomeRequestDTO;
import com.example.pfms.response.IncomeResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IncomeServiceImpl implements IncomeService{
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserService userService;

    public List<Income> getAllIncomes(Long id){
        return incomeRepository.findByUserId(id);
    }

    public Income getIncomeById(Long id){
        return incomeRepository.findById(id).orElse(null);
    }

    @Override
    public Income updateIncome(Long id, IncomeRequestDTO incomeRequestDTO) {
        Income income = getIncomeById(id);
        if(Objects.equals(income.getUser().getId(), incomeRequestDTO.getUserId())){
            BeanUtils.copyProperties(incomeRequestDTO, income);
            return incomeRepository.save(income);
        }
        return null;

    }

    public Income saveIncome(Income income){
        return incomeRepository.save(income);
    }

    public void deleteIncome(Long id){
        incomeRepository.deleteById(id);
    }
}
