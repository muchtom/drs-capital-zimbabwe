package com.buffaloVentures.loan.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Credit bureau response from Credit Bureau API")
public class CreditBureauResponse {
    
    @Schema(description = "Whether the credit check was successful", example = "true")
    private Boolean success;
    
    @Schema(description = "Applicant's credit score", example = "650")
    private Integer creditScore;
    
    @Schema(description = "Number of active loans", example = "1")
    private Integer activeLoansCount;
    
    @Schema(description = "Whether applicant has active defaults", example = "false")
    private Boolean hasActiveDefaults;
    
    @Schema(description = "Error message if credit check failed", example = "Service unavailable")
    private String errorMessage;
}

