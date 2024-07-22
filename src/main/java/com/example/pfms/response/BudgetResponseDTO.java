package com.example.pfms.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BudgetResponseDTO {
    private Long id;
    private String category;
    private double limit;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserResponseDTO userResponseDTO;
}
