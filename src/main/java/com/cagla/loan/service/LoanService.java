package com.cagla.loan.service;

import com.cagla.loan.dto.LoanDto;
import com.cagla.loan.dto.PaymentResultDto;
import com.cagla.loan.exception.ResourceNotFoundException;
import com.cagla.loan.model.Customer;
import com.cagla.loan.model.Loan;
import com.cagla.loan.model.LoanInstallment;
import com.cagla.loan.repository.CustomerRepository;
import com.cagla.loan.repository.LoanInstallmentRepository;
import com.cagla.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanInstallmentRepository loanInstallmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a loan after validating the customer's eligibility.
     */
    public Loan createLoan(LoanDto loanDTO) {
        // Fetch customer by ID
        Optional<Customer> customerOptional = customerRepository.findById(loanDTO.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new RuntimeException("Customer not found with ID: " + loanDTO.getCustomerId());
        }

        Customer customer = customerOptional.get();

        // Validate customer loan limit
        double currentLoanLimit = getLoanLimit(customer);
        double requestedLoanAmount = loanDTO.getLoanAmount();
        if (requestedLoanAmount > currentLoanLimit) {
            throw new RuntimeException("Insufficient loan limit. Maximum allowable: " + currentLoanLimit);
        }

        // Validate number of installments
        int numberOfInstallments = loanDTO.getNumberOfInstallments();
        if (!(numberOfInstallments == 6 || numberOfInstallments == 9 || numberOfInstallments == 12 || numberOfInstallments == 24)) {
            throw new RuntimeException("Invalid number of installments. Allowed values are 6, 9, 12, 24.");
        }

        // Validate interest rate
        double interestRate = loanDTO.getInterestRate();
        if (interestRate < 0.1 || interestRate > 0.5) {
            throw new RuntimeException("Invalid interest rate. Must be between 0.1 and 0.5.");
        }

        // Calculate total loan amount including interest
        double totalAmount = requestedLoanAmount * (1 + interestRate);
        double installmentAmount = totalAmount / numberOfInstallments;

        // Create Loan object
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(requestedLoanAmount);
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setCreateDate(new Date());
        loan.setPaid(false);
        loan.setInterestRate(interestRate);

        Loan savedLoan = loanRepository.save(loan);

        // Create Loan Installments
        for (int i = 1; i <= numberOfInstallments; i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setLoan(savedLoan);
            installment.setAmount(installmentAmount);
            installment.setPaid(false);
            installment.setPaidAmount(0);
            installment.setDueDate(calculateDueDate(i));
            loanInstallmentRepository.save(installment);
        }

        return savedLoan;
    }

    /**
     * Calculates the customer's remaining loan limit.
     */
    private double getLoanLimit(Customer customer) {
        double totalLoans = customer.getLoans().stream()
                .mapToDouble(Loan::getLoanAmount)
                .sum();
        double maximumLimit = 50000.0; // Example maximum limit per customer
        return maximumLimit - totalLoans;
    }

    /**
     * Calculates the due date for an installment.
     */
    private Date calculateDueDate(int installmentNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, installmentNumber);
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to first day of the month
        return calendar.getTime();
    }

    public List<Loan> listLoans(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public List<LoanInstallment> listInstallments(Long loanId) {
        return loanInstallmentRepository.findByLoanId(loanId);
    }

    public PaymentResultDto payLoan(Long loanId, double amount) {
        // Validate loan ID
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + loanId));

        // Validate amount
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }

        // Fetch the current date and calculate the maximum payable date
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 3); // Add 3 months
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        Date maxPayableDate = calendar.getTime();

        List<LoanInstallment> installments = loanInstallmentRepository.findByLoanId(loanId);
        int paidInstallmentsCount = 0;
        double totalPaidAmount = 0;

        double remainingAmount = amount;
        for (LoanInstallment installment : installments) {
            // Skip installments with a due date beyond the 3-month limit or already paid
            if (installment.getDueDate().after(maxPayableDate) || installment.isPaid()) {
                continue;
            }

            if (remainingAmount >= installment.getAmount() ) {
                installment.setPaid(true);
                installment.setPaidAmount(installment.getAmount());
                remainingAmount -= installment.getAmount();
                totalPaidAmount += installment.getAmount();
                paidInstallmentsCount++;
            }
        }
        // Save updated installments
        loanInstallmentRepository.saveAll(installments);

        // Update loan status if fully paid
        boolean isLoanPaidCompletely = loanInstallmentRepository.findByLoanIdAndPaidFalse(loanId).isEmpty();
        if (isLoanPaidCompletely) {
            Optional<Loan> loanOptional = loanRepository.findById(loanId);
            if (loanOptional.isPresent()) {
                Loan loantemp = loanOptional.get();
                loantemp.setPaid(true);
                loanRepository.save(loantemp);
            }
        }

        // Return the payment result (optional)
        System.out.println("Paid Installments Count: " + paidInstallmentsCount);
        System.out.println("Total Paid Amount: " + totalPaidAmount);
        System.out.println("Loan Paid Completely: " + isLoanPaidCompletely);

        // Return result as PaymentResultDto
        return new PaymentResultDto(paidInstallmentsCount, totalPaidAmount, isLoanPaidCompletely);
    }
}
