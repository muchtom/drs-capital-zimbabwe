package com.buffaloVentures.loan.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Loan eligibility response")
public class EligibilityResponse {
    
    @Schema(description = "Whether the applicant is eligible", example = "true")
    private Boolean eligible;
    
    @Schema(description = "Reasons for decline (if not eligible)", example = "Insufficient salary")
    private List<String> declineReasons;
    
    @Schema(description = "Monthly repayment amount", example = "4166.67")
    private BigDecimal monthlyRepayment;
    
    @Schema(description = "Applicant's monthly salary", example = "15000.00")
    private BigDecimal monthlySalary;
    
    @Schema(description = "Applicant's credit score", example = "650")
    private Integer creditScore;
    
    @Schema(description = "Number of active loans", example = "1")
    private Integer activeLoansCount;
    
    @Schema(description = "Whether applicant has active defaults", example = "false")
    private Boolean hasActiveDefaults;
}

