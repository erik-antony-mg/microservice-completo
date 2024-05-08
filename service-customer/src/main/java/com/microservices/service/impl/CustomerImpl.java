package com.microservices.service.impl;

import com.microservices.entities.Customer;
import com.microservices.entities.Region;
import com.microservices.exception.CustomerNotFound;
import com.microservices.repository.CustomerRepository;
import com.microservices.repository.RegionRepository;
import com.microservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RegionRepository regionRepository;
    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getAllCustomerByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer getOneCustomeById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFound("cliente no encontrado"));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        String regionName= customer.getRegion().getName().toLowerCase();

        Optional<Customer> customerOptional =customerRepository.findByDni(customer.getDni());
        if (customerOptional.isPresent()){
            return customerOptional.get();
        }
        if (customer.getPhotoUrl()==null){
            customer.setPhotoUrl("no-image.jpg");
        }
        customer.setState("CREATED");

        Optional<Region> regionOptional=regionRepository.findByName(regionName);
        if (regionOptional.isPresent()){
            customer.setRegion(regionOptional.get());
        }else {
            customer.setRegion(regionRepository.save(customer.getRegion()));
        }


        return customerRepository.save(customer);
    }

    @Override
    public Customer editCustomer(Long customerId, Customer customer) {
        Optional<Customer> customerOptional=customerRepository.findById(customerId);
        if (customerOptional.isPresent()){
           customerOptional.get().setFirstName(customer.getFirstName());
           customerOptional.get().setLastName(customer.getLastName());
           customerOptional.get().setDni(customer.getDni());
           customerOptional.get().setEmail(customer.getEmail());

           Optional<Region> regionOptional= regionRepository.findByName(customer.getRegion().getName().toLowerCase());

           if (regionOptional.isPresent()){
               customerOptional.get().setRegion(regionOptional.get());
           }else {
               Region region=regionRepository.save(customer.getRegion());
               customerOptional.get().setRegion(region);
           }
            return  customerRepository.save(customerOptional.get());
        }
       return null;
    }

    @Override
    public Customer deleteCustomer(Long customerId) {
       Customer customerDb= customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFound("cliente no encontrado"));
        customerDb.setState("DELETE");
        return customerDb;
    }
}
