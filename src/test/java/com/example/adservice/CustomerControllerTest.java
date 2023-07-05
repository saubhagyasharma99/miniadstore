package com.example.adservice;

import com.example.adservice.controller.CustomerController;
import com.example.adservice.model.Customer;
import com.example.adservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerByEmail() {
        String email = "test@example.com";
        Customer customer = new Customer();

        when(customerService.getCustomerByEmail(email)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService, times(1)).getCustomerByEmail(email);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        Customer createdCustomer = new Customer();

        when(customerService.createCustomer(customer)).thenReturn(createdCustomer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCustomer, response.getBody());
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    void testUpdateCustomer() {
        String email = "test@example.com";
        Customer customer = new Customer();
        Customer updatedCustomer = new Customer();

        when(customerService.updateCustomer(email, customer)).thenReturn(updatedCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(email, customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
        verify(customerService, times(1)).updateCustomer(email, customer);
    }

    @Test
    void testDeleteCustomer() {
        String email = "test@example.com";

        customerController.deleteCustomer(email);

        verify(customerService, times(1)).deleteCustomer(email);
    }
}
