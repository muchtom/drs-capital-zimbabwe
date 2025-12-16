package com.buffaloVentures.loan.service;

import com.buffaloVentures.loan.DTO.SalaryVerificationResponse;

public interface SalaryVerificationService {
    
    SalaryVerificationResponse verifySalary(String nationalId);
}

