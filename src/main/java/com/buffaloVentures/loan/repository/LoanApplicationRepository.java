package com.buffaloVentures.loan.repository;

import com.buffaloVentures.loan.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    
    List<LoanApplication> findByNationalIdOrderByApplicationDateDesc(String nationalId);
}

