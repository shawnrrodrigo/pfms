package com.example.pfms.service;

import com.example.pfms.model.FinancialReport;
import org.springframework.stereotype.Component;

@Component
public interface FinancialReportService {
    FinancialReport generateMonthlyReport(Long userId, int month, int year);
    FinancialReport generateQuarterlyReport(Long userId, int quarter, int year);
    FinancialReport generateYearlyReport(Long userId, int year);
}
