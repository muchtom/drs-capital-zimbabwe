package com.buffaloVentures.loan.api;

import com.buffaloVentures.loan.DTO.EligibilityResponse;
import com.buffaloVentures.loan.DTO.LoanApplicationRequest;
import com.buffaloVentures.loan.service.LoanEligibilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan Eligibility", description = "API for checking loan eligibility")
public class LoanEligibilityController {
    
    @Autowired
    private LoanEligibilityService loanEligibilityService;
    
    @PostMapping("/eligibility")
    @Operation(summary = "Check loan eligibility", description = "Checks if an applicant is eligible for a loan based on salary, credit score, and other criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eligibility check completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "503", description = "External service unavailable")
    })
    public ResponseEntity<EligibilityResponse> checkEligibility(@Valid @RequestBody LoanApplicationRequest request) {
        EligibilityResponse response = loanEligibilityService.checkEligibility(request);
        return ResponseEntity.ok(response);
    }
}

