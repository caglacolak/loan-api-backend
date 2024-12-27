package com.cagla.loan.service;

import com.cagla.loan.dto.LoanDto;
import com.cagla.loan.dto.PaymentResultDto;
import com.cagla.loan.model.Customer;
import com.cagla.loan.model.Loan;
import com.cagla.loan.model.LoanInstallment;
import com.cagla.loan.repository.CustomerRepository;
import com.cagla.loan.repository.LoanInstallmentRepository;
import com.cagla.loan.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentRepository loanInstallmentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoan_Success() {
        LoanDto loanDto = new LoanDto();
        loanDto.setCustomerId(1L);
        loanDto.setLoanAmount(10000);
        loanDto.setNumberOfInstallments(12);
        loanDto.setInterestRate(0.2);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLoans(new ArrayList<>());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(loanInstallmentRepository.save(any(LoanInstallment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan loan = loanService.createLoan(loanDto);

        assertNotNull(loan);
        assertEquals(10000, loan.getLoanAmount());
        assertEquals(12, loan.getNumberOfInstallments());
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(loanInstallmentRepository, times(12)).save(any(LoanInstallment.class));
    }

    @Test
    void testCreateLoan_CustomerNotFound() {
        LoanDto loanDto = new LoanDto();
        loanDto.setCustomerId(1L);
        loanDto.setLoanAmount(10000);
        loanDto.setNumberOfInstallments(12);
        loanDto.setInterestRate(0.2);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> loanService.createLoan(loanDto));

        assertEquals("Customer not found with ID: 1", exception.getMessage());
    }

    @Test
    void testPayLoan_Success() {
        // Mock loan
        Loan mockLoan = new Loan();
        mockLoan.setId(1L);
        mockLoan.setLoanAmount(1000.0);
        mockLoan.setNumberOfInstallments(3);
        mockLoan.setPaid(false);

        // Mock installments with due dates
        LoanInstallment installment1 = new LoanInstallment();
        installment1.setId(1L);
        installment1.setLoan(mockLoan);
        installment1.setAmount(333.33);
        installment1.setPaid(false);
        installment1.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // Due tomorrow

        LoanInstallment installment2 = new LoanInstallment();
        installment2.setId(2L);
        installment2.setLoan(mockLoan);
        installment2.setAmount(333.33);
        installment2.setPaid(false);
        installment2.setDueDate(new Date(System.currentTimeMillis() + (2 * 86400000))); // Due in 2 days

        LoanInstallment installment3 = new LoanInstallment();
        installment3.setId(3L);
        installment3.setLoan(mockLoan);
        installment3.setAmount(333.34);
        installment3.setPaid(false);
        installment3.setDueDate(new Date(System.currentTimeMillis() + (3 * 86400000))); // Due in 3 days

        List<LoanInstallment> installments = Arrays.asList(installment1, installment2, installment3);

        // Mock behavior
        when(loanRepository.findById(1L)).thenReturn(Optional.of(mockLoan));
        when(loanInstallmentRepository.findByLoanId(1L)).thenReturn(installments);

        // Call the method
        PaymentResultDto result = loanService.payLoan(1L, 1000.0);

        // Verify repository interactions
        verify(loanInstallmentRepository, times(1)).saveAll(installments);

        // Assertions
        assertEquals(3, result.getInstallmentsPaid());
        assertEquals(1000.0, result.getTotalAmountSpent());
        assertTrue(result.isLoanPaidCompletely());
    }

    @Test
    void testListLoans() {
        Long customerId = 1L;
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan());
        loans.add(new Loan());

        when(loanRepository.findByCustomerId(customerId)).thenReturn(loans);

        List<Loan> result = loanService.listLoans(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(loanRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    void testListInstallments() {
        Long loanId = 1L;
        List<LoanInstallment> installments = new ArrayList<>();
        installments.add(new LoanInstallment());
        installments.add(new LoanInstallment());

        when(loanInstallmentRepository.findByLoanId(loanId)).thenReturn(installments);

        List<LoanInstallment> result = loanService.listInstallments(loanId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(loanInstallmentRepository, times(1)).findByLoanId(loanId);
    }
}
