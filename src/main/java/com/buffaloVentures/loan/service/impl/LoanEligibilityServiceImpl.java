package com.buffaloVentures.loan.service.impl;

import com.buffaloVentures.loan.DTO.CreditBureauResponse;
import com.buffaloVentures.loan.DTO.EligibilityResponse;
import com.buffaloVentures.loan.DTO.LoanApplicationRequest;
import com.buffaloVentures.loan.DTO.SalaryVerificationResponse;
import com.buffaloVentures.loan.entity.LoanApplication;
import com.buffaloVentures.loan.exception.ServiceUnavailableException;
import com.buffaloVentures.loan.repository.LoanApplicationRepository;
import com.buffaloVentures.loan.service.CreditBureauService;
import com.buffaloVentures.loan.service.LoanEligibilityService;
import com.buffaloVentures.loan.service.SalaryVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {
    
    @Autowired
    private SalaryVerificationService salaryVerificationService;
    
    @Autowired
    private CreditBureauService creditBureauService;
    
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    
    private static final BigDecimal MIN_SALARY_MULTIPLIER = new BigDecimal("3.0");
    private static final int MIN_CREDIT_SCORE = 600;
    private static final int MAX_ACTIVE_LOANS = 3;
    
    @Override
    public EligibilityResponse checkEligibility(LoanApplicationRequest request) {
        String nationalId = request.getNationalId();
        BigDecimal loanAmount = request.getLoanAmount();
        int termInMonths = request.getTermInMonths();
        
        // Calculate monthly repayment
        BigDecimal monthlyRepayment = calculateMonthlyRepayment(loanAmount, termInMonths);
        
        // Get salary info
        SalaryVerificationResponse salaryResponse = salaryVerificationService.verifySalary(nationalId);
        if (!salaryResponse.getSuccess()) {
            throw new ServiceUnavailableException("Salary service unavailable: " + salaryResponse.getErrorMessage());
        }
        
        // Get credit info
        CreditBureauResponse creditResponse = creditBureauService.checkCreditHistory(nationalId);
        if (!creditResponse.getSuccess()) {
            throw new ServiceUnavailableException("Credit bureau unavailable: " + creditResponse.getErrorMessage());
        }
        
        // Check eligibility rules
        List<String> reasons = new ArrayList<>();
        boolean isEligible = true;
        
        // Rule 1: Salary must be 3x monthly repayment
        BigDecimal requiredSalary = monthlyRepayment.multiply(MIN_SALARY_MULTIPLIER);
        if (salaryResponse.getMonthlySalary().compareTo(requiredSalary) < 0) {
            isEligible = false;
            reasons.add("Salary too low. Need: " + requiredSalary + ", Have: " + salaryResponse.getMonthlySalary());
        }
        
        // Rule 2: Credit score must be 600+
        if (creditResponse.getCreditScore() < MIN_CREDIT_SCORE) {
            isEligible = false;
            reasons.add("Credit score too low. Need: " + MIN_CREDIT_SCORE + ", Have: " + creditResponse.getCreditScore());
        }
        
        // Rule 3: No defaults
        if (creditResponse.getHasActiveDefaults()) {
            isEligible = false;
            reasons.add("Has active defaults");
        }
        
        // Rule 4: Max 3 active loans
        if (creditResponse.getActiveLoansCount() >= MAX_ACTIVE_LOANS) {
            isEligible = false;
            reasons.add("Too many loans. Max: " + MAX_ACTIVE_LOANS + ", Current: " + creditResponse.getActiveLoansCount());
        }
        
        // Create response
        EligibilityResponse response = new EligibilityResponse();
        response.setEligible(isEligible);
        response.setDeclineReasons(isEligible ? null : reasons);
        response.setMonthlyRepayment(monthlyRepayment);
        response.setMonthlySalary(salaryResponse.getMonthlySalary());
        response.setCreditScore(creditResponse.getCreditScore());
        response.setActiveLoansCount(creditResponse.getActiveLoansCount());
        response.setHasActiveDefaults(creditResponse.getHasActiveDefaults());
        
        // Save to database
        LoanApplication app = new LoanApplication();
        app.setNationalId(nationalId);
        app.setLoanAmount(loanAmount);
        app.setTermInMonths(termInMonths);
        app.setEligible(isEligible);
        if (!isEligible) {
            app.setDeclineReason(String.join("; ", reasons));
        }
        loanApplicationRepository.save(app);
        
        return response;
    }
    
    private BigDecimal calculateMonthlyRepayment(BigDecimal loanAmount, int termInMonths) {
        // 10% annual interest rate
        double annualRate = 0.10;
        double monthlyRate = annualRate / 12.0;
        
        // Simple calculation: principal + interest divided by months
        // For simplicity, using basic formula
        double totalAmount = loanAmount.doubleValue() * (1 + annualRate);
        double monthlyPayment = totalAmount / termInMonths;
        
        return new BigDecimal(monthlyPayment).setScale(2, RoundingMode.HALF_UP);
    }
}

