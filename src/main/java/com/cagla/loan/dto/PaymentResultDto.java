package com.cagla.loan.dto;

public class PaymentResultDto {
    private int installmentsPaid;
    private double totalAmountSpent;
    private boolean loanPaidCompletely;

    public PaymentResultDto(int installmentsPaid, double totalAmountSpent, boolean loanPaidCompletely) {
        this.installmentsPaid = installmentsPaid;
        this.totalAmountSpent = totalAmountSpent;
        this.loanPaidCompletely = loanPaidCompletely;
    }

    public int getInstallmentsPaid() {
        return installmentsPaid;
    }

    public void setInstallmentsPaid(int installmentsPaid) {
        this.installmentsPaid = installmentsPaid;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public void setTotalAmountSpent(double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }

    public boolean isLoanPaidCompletely() {
        return loanPaidCompletely;
    }

    public void setLoanPaidCompletely(boolean loanPaidCompletely) {
        this.loanPaidCompletely = loanPaidCompletely;
    }
}
