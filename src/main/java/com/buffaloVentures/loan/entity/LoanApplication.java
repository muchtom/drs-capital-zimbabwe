package com.buffaloVentures.loan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nationalId;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;
    
    @Column(nullable = false)
    private Integer termInMonths;
    
    @Column(nullable = false)
    private Boolean eligible;
    
    @Column(length = 500)
    private String declineReason;
    
    @Column(nullable = false)
    private LocalDateTime applicationDate;
    
    @PrePersist
    protected void onCreate() {
        applicationDate = LocalDateTime.now();
    }
}

