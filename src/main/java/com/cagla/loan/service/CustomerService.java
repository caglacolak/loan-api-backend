package com.cagla.loan.service;

import com.cagla.loan.model.Customer;
import com.cagla.loan.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get a customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Update customer credit limit
    public Customer updateCreditLimit(Long id, double newCreditLimit) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (!customerOptional.isPresent()) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        Customer customer = customerOptional.get();
        customer.setCreditLimit(newCreditLimit);
        return customerRepository.save(customer);
    }

    // Update the used credit limit for the customer
    public Customer updateUsedCreditLimit(Long id, double newUsedCreditLimit) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (!customerOptional.isPresent()) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        Customer customer = customerOptional.get();
        customer.setUsedCreditLimit(newUsedCreditLimit);
        return customerRepository.save(customer);
    }
}
