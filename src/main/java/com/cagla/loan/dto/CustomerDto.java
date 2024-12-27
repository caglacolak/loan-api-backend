package com.cagla.loan.dto;

public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private double creditLimit;
    private double usedCreditLimit;

    // Constructors
    public CustomerDto() {}

    public CustomerDto(Long id, String name, String surname, double creditLimit, double usedCreditLimit) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.creditLimit = creditLimit;
        this.usedCreditLimit = usedCreditLimit;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getUsedCreditLimit() {
        return usedCreditLimit;
    }

    public void setUsedCreditLimit(double usedCreditLimit) {
        this.usedCreditLimit = usedCreditLimit;
    }
}
