package com.buffaloVentures.loan.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Salary verification response from Salary Verification API")
public class SalaryVerificationResponse {
    
    @Schema(description = "Whether the verification was successful", example = "true")
    private Boolean success;
    
    @Schema(description = "Applicant's monthly salary", example = "15000.00")
    private BigDecimal monthlySalary;
    
    @Schema(description = "Error message if verification failed", example = "Applicant not found")
    private String errorMessage;
}

