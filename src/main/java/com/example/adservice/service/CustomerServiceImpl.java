package com.example.adservice.service;

import com.example.adservice.exception.CustomerNotFoundException;
import com.example.adservice.model.Customer;
import com.example.adservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findById(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String email, Customer customer) {
        Customer existingCustomer = getCustomerByEmail(email);
        existingCustomer.setName(customer.getName());
        existingCustomer.setDateOfBirth(customer.getDateOfBirth());
        existingCustomer.setOccupation(customer.getOccupation());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(String email) {
        Customer customer = getCustomerByEmail(email);
        customerRepository.delete(customer);
    }
}