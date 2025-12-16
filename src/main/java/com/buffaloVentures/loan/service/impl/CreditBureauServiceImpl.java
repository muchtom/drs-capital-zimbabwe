package com.buffaloVentures.loan.service.impl;

import com.buffaloVentures.loan.DTO.CreditBureauResponse;
import com.buffaloVentures.loan.service.CreditBureauService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CreditBureauServiceImpl implements CreditBureauService {
    
    // Mock data - in real app this would call external API
    private Map<String, CreditData> creditData = new HashMap<>();
    private Random random = new Random();
    
    public CreditBureauServiceImpl() {
        // Add some test data
        creditData.put("1234567890", new CreditData(650, 1, false));
        creditData.put("0987654321", new CreditData(750, 0, false));
        creditData.put("1122334455", new CreditData(550, 2, true));
        creditData.put("5566778899", new CreditData(800, 0, false));
    }
    
    @Override
    public CreditBureauResponse checkCreditHistory(String nationalId) {
        // Simulate API delay
        try {
            Thread.sleep(150 + random.nextInt(250));
        } catch (InterruptedException e) {
            // ignore
        }
        
        // Sometimes service fails (5% chance)
        if (random.nextInt(100) < 5) {
            return new CreditBureauResponse(false, null, null, null, "Service unavailable");
        }
        
        // Check if we have credit data
        CreditData data = creditData.get(nationalId);
        
        if (data != null) {
            return new CreditBureauResponse(true, data.creditScore, 
                    data.activeLoansCount, data.hasActiveDefaults, null);
        }
        
        // New applicant - generate random data
        int score = 500 + random.nextInt(400);
        int loans = random.nextInt(4);
        boolean defaults = random.nextInt(10) < 2;
        
        CreditData newData = new CreditData(score, loans, defaults);
        creditData.put(nationalId, newData);
        
        return new CreditBureauResponse(true, score, loans, defaults, null);
    }
    
    // Helper class
    private class CreditData {
        int creditScore;
        int activeLoansCount;
        boolean hasActiveDefaults;
        
        CreditData(int creditScore, int activeLoansCount, boolean hasActiveDefaults) {
            this.creditScore = creditScore;
            this.activeLoansCount = activeLoansCount;
            this.hasActiveDefaults = hasActiveDefaults;
        }
    }
}

