package com.example.pfms.service;

import com.example.pfms.exception.custom.OperationNotAllowedException;
import com.example.pfms.exception.custom.ResourceNotFoundException;
import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.repository.IncomeRepository;
import com.example.pfms.request.IncomeRequestDTO;
import com.example.pfms.response.IncomeResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService{
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    public List<IncomeResponseDTO> getAllIncomes(Long id){
        User user = userService.findById(id);
        List<Income> incomeList =  incomeRepository.findByUser(user);
        return incomeList.stream()
                .map(income -> {
                    IncomeResponseDTO incomeResponseDTO = new IncomeResponseDTO();
                    UserResponseDTO userResponseDTO = new UserResponseDTO();
                    BeanUtils.copyProperties(income, incomeResponseDTO);
                    userResponseDTO.setName(income.getUser().getName());
                    userResponseDTO.setEmail(income.getUser().getEmail());
                    userResponseDTO.setId(income.getUser().getId());
                    incomeResponseDTO.setUserResponseDTO(userResponseDTO);
                    return incomeResponseDTO;
                }).toList();
    }

    public Income getIncomeById(Long id){
       Optional<Income> optionalIncome = incomeRepository.findById(id);

       return optionalIncome.orElseThrow(()-> new ResourceNotFoundException("Income not found", HttpStatus.NOT_FOUND.value()));

    }

    @Override
    public IncomeResponseDTO updateIncome(Long id, IncomeRequestDTO incomeRequestDTO) {
        Income income = getIncomeById(id);
        User user = userService.findById(incomeRequestDTO.getUserId());
        if(!user.isActive()){
            throw new OperationNotAllowedException("Your status is deactivated", HttpStatus.FORBIDDEN.value());
        }
        if(incomeRequestDTO.getAmount() <= 0){
            throw new OperationNotAllowedException("Amount should be greater than zero", HttpStatus.BAD_REQUEST.value());
        }
        if(!income.getUser().getId().equals(user.getId())){
            throw new OperationNotAllowedException("You don't have access to modify this record", HttpStatus.NOT_FOUND.value());
        }

        if(income.getAmount() != incomeRequestDTO.getAmount()){
            income.setAmount(incomeRequestDTO.getAmount());
        }

        if(incomeRequestDTO.getSource() != null && !income.getSource().equals(incomeRequestDTO.getSource())){
            income.setSource(incomeRequestDTO.getSource());
        }

        LocalDate today = LocalDate.now();
        if (incomeRequestDTO.getDate() != null && !income.getDate().equals(incomeRequestDTO.getDate())) {
            if (incomeRequestDTO.getDate().isAfter(today)) {
                throw new OperationNotAllowedException("Date cannot be in the future", HttpStatus.BAD_REQUEST.value());
            }
            income.setDate(incomeRequestDTO.getDate());
        }

        incomeRepository.save(income);

        IncomeResponseDTO incomeResponseDTO = new IncomeResponseDTO();

        BeanUtils.copyProperties(income, incomeResponseDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        incomeResponseDTO.setUserResponseDTO(userResponseDTO);

        return incomeResponseDTO;
    }

    public Income saveIncome(Income income){
        final Income[] savedIncome = new Income[1];
        transactionService.executeTransaction(() -> {
            savedIncome[0] = incomeRepository.save(income);
        });
        return savedIncome[0];
    }

    public void deleteIncome(Long id){
        incomeRepository.deleteById(id);
    }

    @Override
    public IncomeResponseDTO createIncome(IncomeRequestDTO incomeRequestDTO, Long userId) {
        Income income = new Income();
        if(incomeRequestDTO.getAmount() <= 0){
            throw new OperationNotAllowedException("Amount should be greater than zero", HttpStatus.BAD_REQUEST.value());
        }

        LocalDate today = LocalDate.now();

        if(incomeRequestDTO.getDate() == null){
            throw new OperationNotAllowedException("Date cannot be null", HttpStatus.BAD_REQUEST.value());
        }

        if (incomeRequestDTO.getDate().isAfter(today)) {
            throw new OperationNotAllowedException("Date cannot be in the future", HttpStatus.BAD_REQUEST.value());
        }

        BeanUtils.copyProperties(incomeRequestDTO, income);
        User user = userService.findById(userId);
        if(!user.isActive()){
            throw new OperationNotAllowedException("Your status is deactivated", HttpStatus.FORBIDDEN.value());
        }
        income.setUser(user);
        saveIncome(income);
        IncomeResponseDTO incomeResponseDTO = new IncomeResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        BeanUtils.copyProperties(income, incomeResponseDTO);
        incomeResponseDTO.setUserResponseDTO(userResponseDTO);
        return incomeResponseDTO;
    }
}
