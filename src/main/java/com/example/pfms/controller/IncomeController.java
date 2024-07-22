package com.example.pfms.controller;

import com.example.pfms.model.Income;
import com.example.pfms.model.User;
import com.example.pfms.request.IncomeRequestDTO;
import com.example.pfms.response.IncomeResponseDTO;
import com.example.pfms.response.UserResponseDTO;
import com.example.pfms.security.UserDetailsImpl;
import com.example.pfms.service.IncomeService;
import com.example.pfms.service.IncomeServiceImpl;
import com.example.pfms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public List<IncomeResponseDTO> getAllIncomes(Authentication authentication) {
        Long id = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return incomeService.getAllIncomes(id);
    }

    @GetMapping("/{id}")
    public IncomeResponseDTO getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        IncomeResponseDTO incomeResponseDTO = new IncomeResponseDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(income.getUser().getId());
        userResponseDTO.setName(income.getUser().getName());
        userResponseDTO.setEmail(income.getUser().getEmail());
        BeanUtils.copyProperties(income, incomeResponseDTO);
        incomeResponseDTO.setUserResponseDTO(userResponseDTO);
        return incomeResponseDTO;
    }

    @PostMapping
    public IncomeResponseDTO createIncome(@RequestBody IncomeRequestDTO incomeRequestDTO, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return incomeService.createIncome(incomeRequestDTO, userId);
    }

    @PutMapping("/{id}")
    public IncomeResponseDTO updateIncome(@PathVariable Long id, @RequestBody IncomeRequestDTO incomeRequestDTO, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        incomeRequestDTO.setUserId(userId);
        return incomeService.updateIncome(id, incomeRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
    }
}
