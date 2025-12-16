package com.buffaloVentures.loan.api;

import com.buffaloVentures.loan.DTO.CreditBureauResponse;
import com.buffaloVentures.loan.service.CreditBureauService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
@Tag(name = "Credit Bureau", description = "Mock API for credit bureau service")
public class CreditBureauController {
    
    @Autowired
    private CreditBureauService creditBureauService;
    
    @GetMapping("/check/{nationalId}")
    @Operation(summary = "Check credit history", description = "Mock endpoint to check an applicant's credit history, score, and loan status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credit check completed"),
            @ApiResponse(responseCode = "503", description = "Service temporarily unavailable")
    })
    public ResponseEntity<CreditBureauResponse> checkCreditHistory(
            @Parameter(description = "National ID of the applicant", required = true)
            @PathVariable String nationalId) {
        CreditBureauResponse response = creditBureauService.checkCreditHistory(nationalId);
        return ResponseEntity.ok(response);
    }
}

