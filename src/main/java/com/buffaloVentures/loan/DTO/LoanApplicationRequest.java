package com.buffaloVentures.loan.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for loan eligibility check")
public class LoanApplicationRequest {
    
    @NotBlank(message = "National ID is required")
    @Schema(description = "Applicant's national ID", example = "1234567890", required = true)
    private String nationalId;
    
    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    @Schema(description = "Requested loan amount", example = "50000.00", required = true)
    private BigDecimal loanAmount;
    
    @NotNull(message = "Term in months is required")
    @Positive(message = "Term must be positive")
    @Schema(description = "Loan term in months", example = "12", required = true)
    private Integer termInMonths;
}

