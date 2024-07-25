package com.example.pfms.service;

import com.example.pfms.exception.custom.OperationNotAllowedException;
import com.example.pfms.exception.custom.ResourceNotFoundException;
import com.example.pfms.model.Budget;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.repository.BudgetRepository;
import com.example.pfms.request.BudgetRequestDTO;
import com.example.pfms.response.BudgetResponseDTO;
import com.example.pfms.response.IncomeResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService{
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserService userService;

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }


    public List<BudgetResponseDTO> getAllBudgets(Long id) {
        User user = userService.findById(id);
        List<Budget> budgetList = budgetRepository.findByUser(user);
        return budgetList.stream()
                .map(budget -> {
                    BudgetResponseDTO budgetResponseDTO = new BudgetResponseDTO();
                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(budget, budgetResponseDTO);
                    userResponseDTO.setName(budget.getUser().getName());
                    userResponseDTO.setEmail(budget.getUser().getEmail());
                    userResponseDTO.setId(budget.getUser().getId());
                    budgetResponseDTO.setUserResponseDTO(userResponseDTO);
                    return budgetResponseDTO;
                }).toList();
    }

    public Budget findById(Long id) {
        Optional<Budget> optionalBudget = budgetRepository.findById(id);

        return optionalBudget.orElseThrow(()-> new ResourceNotFoundException("Budget not found", HttpStatus.NOT_FOUND.value()));
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public BudgetResponseDTO createBudget(BudgetRequestDTO budgetRequestDTO, Long userId) {
        User user = userService.findById(userId);
        if(!user.isActive()){
            throw new OperationNotAllowedException("Your status is deactivated", HttpStatus.FORBIDDEN.value());
        }
        Budget budget = new Budget();
        BeanUtils.copyProperties(budgetRequestDTO, budget);
        budget.setUser(user);
        budgetRepository.save(budget);
        BudgetResponseDTO budgetResponseDTO = new BudgetResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        BeanUtils.copyProperties(budget, budgetResponseDTO);
        budgetResponseDTO.setUserResponseDTO(userResponseDTO);
        return budgetResponseDTO;
    }

    @Override
    public BudgetResponseDTO updateBudget(Long id, BudgetRequestDTO budgetRequestDTO) {
        Budget budget = findById(id);
        User user = userService.findById(budgetRequestDTO.getUserId());
        if(!user.isActive()){
            throw new OperationNotAllowedException("Your status is deactivated", HttpStatus.FORBIDDEN.value());
        }
        if(budgetRequestDTO.getLimitAmount() <= 0){
            throw new OperationNotAllowedException("Limit should be greater than zero", HttpStatus.BAD_REQUEST.value());
        }
        if(!budget.getUser().getId().equals(user.getId())){
            throw new OperationNotAllowedException("You don't have access to modify this record", HttpStatus.NOT_FOUND.value());
        }

        if(budget.getLimitAmount() != budgetRequestDTO.getLimitAmount()){
            budget.setLimitAmount(budgetRequestDTO.getLimitAmount());
        }

        if(!budget.getCategory().equals(budgetRequestDTO.getCategory())){
            budget.setCategory(budgetRequestDTO.getCategory());
        }

        if (!budget.getStartDate().equals(budgetRequestDTO.getStartDate()) || !budget.getEndDate().equals(budgetRequestDTO.getEndDate())) {
            if (budgetRequestDTO.getStartDate().isAfter(budgetRequestDTO.getEndDate())) {
                throw new OperationNotAllowedException("Start date cannot be after end date", HttpStatus.BAD_REQUEST.value());
            }

            budget.setStartDate(budgetRequestDTO.getStartDate());
            budget.setEndDate(budgetRequestDTO.getEndDate());
        }

        budgetRepository.save(budget);

        BudgetResponseDTO budgetResponseDTO = new BudgetResponseDTO();

        BeanUtils.copyProperties(budget, budgetResponseDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        budgetResponseDTO.setUserResponseDTO(userResponseDTO);

        return budgetResponseDTO;

    }

    @Override
    public Double getBudgetSpending(Long id) {
        return null;
    }
}
