package com.buffaloVentures.loan.service;

import com.buffaloVentures.loan.DTO.CreditBureauResponse;

public interface CreditBureauService {
    
    CreditBureauResponse checkCreditHistory(String nationalId);
}

