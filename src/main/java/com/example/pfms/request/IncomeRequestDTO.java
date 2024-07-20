package com.example.pfms.request;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeRequestDTO {
    private double amount;
    private String source;
    private LocalDate date;
    private Long userId;
}
