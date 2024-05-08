package com.microservices.service;

import com.microservices.entities.Customer;
import com.microservices.entities.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {

    List<Customer> getAllCustomer();
    List<Customer> getAllCustomerByRegion(Region region);
    Customer getOneCustomeById(Long customerId);
    Customer createCustomer(Customer customer);
    Customer editCustomer(Long customerId,Customer customer);
    Customer deleteCustomer(Long customerId);

}
