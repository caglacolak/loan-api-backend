package com.cagla.loan.dto;

import java.util.Date;

public class LoanInstallmentDto {
    private Long id;
    private Long loanId;
    private double amount;
    private double paidAmount;
    private Date dueDate;
    private Date paymentDate;
    private boolean isPaid;

    // Constructors
    public LoanInstallmentDto() {}

    public LoanInstallmentDto(Long id, Long loanId, double amount, double paidAmount, Date dueDate, Date paymentDate, boolean isPaid) {
        this.id = id;
        this.loanId = loanId;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        isPaid = isPaid;
    }
}
