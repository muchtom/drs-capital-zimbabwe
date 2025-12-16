package com.buffaloVentures.loan.api;

import com.buffaloVentures.loan.DTO.SalaryVerificationResponse;
import com.buffaloVentures.loan.service.SalaryVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salary")
@Tag(name = "Salary Verification", description = "Mock API for salary verification service")
public class SalaryVerificationController {
    
    @Autowired
    private SalaryVerificationService salaryVerificationService;
    
    @GetMapping("/verify/{nationalId}")
    @Operation(summary = "Verify salary", description = "Mock endpoint to verify an applicant's monthly salary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salary verification completed"),
            @ApiResponse(responseCode = "503", description = "Service temporarily unavailable")
    })
    public ResponseEntity<SalaryVerificationResponse> verifySalary(
            @Parameter(description = "National ID of the applicant", required = true)
            @PathVariable String nationalId) {
        SalaryVerificationResponse response = salaryVerificationService.verifySalary(nationalId);
        return ResponseEntity.ok(response);
    }
}

