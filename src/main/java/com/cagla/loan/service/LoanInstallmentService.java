package com.cagla.loan.service;

import com.cagla.loan.model.Loan;
import com.cagla.loan.model.LoanInstallment;
import com.cagla.loan.repository.LoanInstallmentRepository;
import com.cagla.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanInstallmentService {

    @Autowired
    private LoanInstallmentRepository loanInstallmentRepository;

    @Autowired
    private LoanRepository loanRepository;

    /**
     * Retrieves all installments associated with a given loan ID.
     *
     * @param loanId the ID of the loan
     * @return a list of LoanInstallment objects
     */
    public List<LoanInstallment> getInstallmentsByLoanId(Long loanId) {
        return loanInstallmentRepository.findByLoanId(loanId);
    }

    /**
     * Marks a specific installment as paid by its ID.
     *
     * @param installmentId the ID of the installment to be paid
     * @param amountPaid    the amount paid for the installment
     */
    public void payInstallment(Long installmentId, double amountPaid) {
        Optional<LoanInstallment> optionalInstallment = loanInstallmentRepository.findById(installmentId);
        if (!optionalInstallment.isPresent()) {
            throw new RuntimeException("Installment not found with ID: " + installmentId);
        }

        LoanInstallment installment = optionalInstallment.get();
        if (installment.isPaid()) {
            throw new RuntimeException("This installment has already been paid.");
        }

        if (amountPaid < installment.getAmount()) {
            throw new RuntimeException("Paid amount is less than the required installment amount.");
        }

        installment.setPaid(true);
        installment.setPaidAmount(installment.getAmount());
        loanInstallmentRepository.save(installment);
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param loanId the ID of the loan
     * @return the Loan object, if found
     */
    public Loan getLoanById(Long loanId) {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (!optionalLoan.isPresent()) {
            throw new RuntimeException("Loan not found with ID: " + loanId);
        }
        return optionalLoan.get();
    }
}
