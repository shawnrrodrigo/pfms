package com.example.pfms.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseRequestDTO {
    private double amount;
    private String category;
    private String source;
    private LocalDate date;
    private Long userId;
}
