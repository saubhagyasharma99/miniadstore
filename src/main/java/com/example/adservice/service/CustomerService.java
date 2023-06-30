package com.example.adservice.service;

import com.example.adservice.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerByEmail(String email);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(String email, Customer customer);

    void deleteCustomer(String email);
}