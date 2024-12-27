package com.cagla.loan.controller;

import com.cagla.loan.dto.LoanDto;
import com.cagla.loan.dto.PaymentResultDto;
import com.cagla.loan.model.Loan;
import com.cagla.loan.model.LoanInstallment;
import com.cagla.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Endpoint to create a new loan
    @PostMapping("/create")
    public Loan createLoan(@RequestBody LoanDto loanDTO) {
        return loanService.createLoan(loanDTO);
    }

    // Endpoint to list loans for a given customer
    @GetMapping("/customer/{customerId}")
    public List<Loan> getLoansByCustomer(@PathVariable Long customerId) {
        return loanService.listLoans(customerId);
    }

    // Endpoint to list all installments for a given loan
    @GetMapping("/installments/{loanId}")
    public List<LoanInstallment> getInstallmentsByLoan(@PathVariable Long loanId) {
        return loanService.listInstallments(loanId);
    }

    // Endpoint to pay loan installments
    @PostMapping("/pay/{loanId}")
    public PaymentResultDto payLoan(@PathVariable Long loanId, @RequestParam double amount) {
        return loanService.payLoan(loanId, amount);
    }
}
