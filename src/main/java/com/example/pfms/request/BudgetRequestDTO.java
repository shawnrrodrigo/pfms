package com.example.pfms.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BudgetRequestDTO {
    private String category;
    private double limitAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;
}
