package com.buffaloVentures.loan.service;

import com.buffaloVentures.loan.DTO.EligibilityResponse;
import com.buffaloVentures.loan.DTO.LoanApplicationRequest;

public interface LoanEligibilityService {
    
    EligibilityResponse checkEligibility(LoanApplicationRequest request);
}

