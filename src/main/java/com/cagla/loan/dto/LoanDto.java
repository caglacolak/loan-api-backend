package com.cagla.loan.dto;

public class LoanDto {
    private Long customerId;
    private double loanAmount;
    private int numberOfInstallments;
    private double interestRate;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "LoanDto{" +
                "customerId=" + customerId +
                ", loanAmount=" + loanAmount +
                ", numberOfInstallments=" + numberOfInstallments +
                ", interestRate=" + interestRate +
                '}';
    }
}
