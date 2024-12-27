package com.cagla.loan.controller;

import com.cagla.loan.model.Customer;
import com.cagla.loan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create a new customer
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Get all customers
    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // Update customer credit limit
    @PutMapping("/update/{id}/creditLimit")
    public Customer updateCreditLimit(@PathVariable Long id, @RequestParam double newCreditLimit) {
        return customerService.updateCreditLimit(id, newCreditLimit);
    }

    // Update customer used credit limit
    @PutMapping("/update/{id}/usedCreditLimit")
    public Customer updateUsedCreditLimit(@PathVariable Long id, @RequestParam double newUsedCreditLimit) {
        return customerService.updateUsedCreditLimit(id, newUsedCreditLimit);
    }
}
