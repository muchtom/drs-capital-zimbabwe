package com.buffaloVentures.loan.service.impl;

import com.buffaloVentures.loan.DTO.SalaryVerificationResponse;
import com.buffaloVentures.loan.service.SalaryVerificationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SalaryVerificationServiceImpl implements SalaryVerificationService {
    
    // Mock data - in real app this would call external API
    private Map<String, BigDecimal> salaryData = new HashMap<>();
    private Random random = new Random();
    
    public SalaryVerificationServiceImpl() {
        // Add some test data
        salaryData.put("1234567890", new BigDecimal("15000.00"));
        salaryData.put("0987654321", new BigDecimal("25000.00"));
        salaryData.put("1122334455", new BigDecimal("8000.00"));
        salaryData.put("5566778899", new BigDecimal("30000.00"));
    }
    
    @Override
    public SalaryVerificationResponse verifySalary(String nationalId) {
        // Simulate API delay
        try {
            Thread.sleep(100 + random.nextInt(200));
        } catch (InterruptedException e) {
            // ignore
        }
        
        // Sometimes service fails (5% chance)
        if (random.nextInt(100) < 5) {
            return new SalaryVerificationResponse(false, null, "Service unavailable");
        }
        
        // Check if we have salary data
        BigDecimal salary = salaryData.get(nationalId);
        
        if (salary != null) {
            return new SalaryVerificationResponse(true, salary, null);
        }
        
        // New applicant - generate random salary
        double randomSalary = 5000 + random.nextInt(30000);
        BigDecimal newSalary = new BigDecimal(randomSalary).setScale(2, java.math.RoundingMode.HALF_UP);
        salaryData.put(nationalId, newSalary);
        
        return new SalaryVerificationResponse(true, newSalary, null);
    }
}

