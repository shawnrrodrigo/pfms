package com.example.pfms.service;

import com.example.pfms.exception.custom.OperationNotAllowedException;
import com.example.pfms.exception.custom.ResourceNotFoundException;
import com.example.pfms.model.Expense;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.repository.ExpenseRepository;
import com.example.pfms.request.ExpenseRequestDTO;
import com.example.pfms.response.ExpenseResponseDTO;
import com.example.pfms.response.IncomeResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    public List<ExpenseResponseDTO> getAllExpenses(Long id){
        List<Expense> expenseList =  expenseRepository.findByUserId(id);
        return expenseList.stream()
                .map(expense -> {
                    ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();
                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(expense, expenseResponseDTO);
                    userResponseDTO.setName(expense.getUser().getName());
                    userResponseDTO.setEmail(expense.getUser().getEmail());
                    userResponseDTO.setId(expense.getUser().getId());
                    expenseResponseDTO.setUserResponseDTO(userResponseDTO);
                    return expenseResponseDTO;
                }).toList();
    }

    public Expense getExpenseById(Long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        return optionalExpense.orElseThrow(()-> new ResourceNotFoundException("Expense not found", HttpStatus.NOT_FOUND.value()));
    }

    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id){
        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = getExpenseById(id);
        User user = userService.findById(expenseRequestDTO.getUserId());

        if(expenseRequestDTO.getAmount() <= 0){
            throw new OperationNotAllowedException("Amount should be greater than zero", HttpStatus.BAD_REQUEST.value());
        }
        if(!expense.getUser().getId().equals(user.getId())){
            throw new OperationNotAllowedException("You don't have access to modify this record", HttpStatus.NOT_FOUND.value());
        }

        if(expense.getAmount() != expenseRequestDTO.getAmount()){
            expense.setAmount(expenseRequestDTO.getAmount());
        }

        if(expenseRequestDTO.getCategory() != null && !expense.getCategory().equals(expenseRequestDTO.getCategory())){
            expense.setCategory(expenseRequestDTO.getCategory());
        }

        LocalDate today = LocalDate.now();
        if (expenseRequestDTO.getDate() != null && !expense.getDate().equals(expenseRequestDTO.getDate())) {
            if (expenseRequestDTO.getDate().isAfter(today)) {
                throw new OperationNotAllowedException("Date cannot be in the future", HttpStatus.BAD_REQUEST.value());
            }
            expense.setDate(expenseRequestDTO.getDate());
        }

        expenseRepository.save(expense);

        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();

        BeanUtils.copyProperties(expense, expenseResponseDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        expenseResponseDTO.setUserResponseDTO(userResponseDTO);

        return expenseResponseDTO;
    }

    @Override
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO, Long userId) {
        Expense expense = new Expense();
        LocalDate today = LocalDate.now();
        if(expenseRequestDTO.getAmount() <= 0){
            throw new OperationNotAllowedException("Amount should be greater than zero", HttpStatus.BAD_REQUEST.value());
        }
        if (expenseRequestDTO.getDate().isAfter(today)) {
            throw new OperationNotAllowedException("Date cannot be in the future", HttpStatus.BAD_REQUEST.value());
        }
        BeanUtils.copyProperties(expenseRequestDTO, expense);
        User user = userService.findById(userId);
        expense.setUser(user);
        saveExpense(expense);
        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        BeanUtils.copyProperties(expense, expenseResponseDTO);
        expenseResponseDTO.setUserResponseDTO(userResponseDTO);
        return expenseResponseDTO;
    }

    public List<Expense> getExpensesByCategoryAndDateRange(User user, String category, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndCategoryAndDateBetween(user, category, startDate, endDate);
    }
}
