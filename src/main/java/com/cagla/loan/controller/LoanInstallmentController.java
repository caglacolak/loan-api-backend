package com.cagla.loan.controller;

import com.cagla.loan.model.LoanInstallment;
import com.cagla.loan.service.LoanInstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installments")
public class LoanInstallmentController {

    @Autowired
    private LoanInstallmentService loanInstallmentService;

    /**
     * Retrieves all installments for a given loan ID.
     *
     * @param loanId the ID of the loan
     * @return a list of LoanInstallment objects
     */
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<LoanInstallment>> getInstallmentsByLoanId(@PathVariable Long loanId) {
        List<LoanInstallment> installments = loanInstallmentService.getInstallmentsByLoanId(loanId);
        return ResponseEntity.ok(installments);
    }

    /**
     * Marks a specific installment as paid.
     *
     * @param installmentId the ID of the installment to be marked as paid
     * @param amountPaid    the amount paid for the installment
     * @return a success message
     */
    @PostMapping("/{installmentId}/pay")
    public ResponseEntity<String> payInstallment(
            @PathVariable Long installmentId,
            @RequestParam double amountPaid
    ) {
        loanInstallmentService.payInstallment(installmentId, amountPaid);
        return ResponseEntity.ok("Installment marked as paid successfully.");
    }
}
