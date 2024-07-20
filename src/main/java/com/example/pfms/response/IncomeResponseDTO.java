package com.example.pfms.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeResponseDTO {
    private Long id;
    private String source;
    private double amount;
    private LocalDate date;
    private UserResponseDTO userResponseDTO;
}
