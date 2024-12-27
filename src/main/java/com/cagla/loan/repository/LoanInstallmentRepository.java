package com.cagla.loan.repository;

import com.cagla.loan.model.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    List<LoanInstallment> findByLoanId(long loanId);  // This method fetches installments for a specific loan

    @Query(nativeQuery = true, value = "SELECT * FROM loan_installments WHERE loan_id= :loanId and is_paid = FALSE")
    List<LoanInstallment> findByLoanIdAndPaidFalse(@Param("loanId") Long loanId);
}
