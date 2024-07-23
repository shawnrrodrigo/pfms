package com.example.pfms.controller;

import com.example.pfms.model.FinancialReport;
import com.example.pfms.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class FinancialReportController {
    @Autowired
    private FinancialReportService financialReportService;

    @GetMapping("/monthly")
    public ResponseEntity<FinancialReport> getMonthlyReport(@RequestParam Long userId, @RequestParam int month, @RequestParam int year) {
        FinancialReport report = financialReportService.generateMonthlyReport(userId, month, year);
        return ResponseEntity.ok(report);
    }
}
