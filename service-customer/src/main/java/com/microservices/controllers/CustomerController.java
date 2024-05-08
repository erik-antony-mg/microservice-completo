package com.microservices.controllers;

import com.microservices.entities.Customer;
import com.microservices.entities.Region;
import com.microservices.repository.RegionRepository;
import com.microservices.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final RegionRepository regionRepository;

    @GetMapping("/list")
    ResponseEntity<List<Customer>> getAllCustomers(
            @RequestParam(required = false,defaultValue = "") String regionName){
        if (!regionName.isEmpty()){
            String regionNameFinal=regionName.toLowerCase();
            Optional<Region> regionOptional=regionRepository.findByName(regionNameFinal);
            return regionOptional
                    .map(region -> ResponseEntity.ok(customerService.getAllCustomerByRegion(region)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        if (customerService.getAllCustomer().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    @GetMapping("/{customerId}")
    ResponseEntity<Customer> getOneCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.getOneCustomeById(customerId));
    }
    @PostMapping("/create")
    ResponseEntity<Customer> createCustomer(@Validated @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{customerId}")
    ResponseEntity<Customer> deleteCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.deleteCustomer(customerId));
    }
    @PutMapping("/edit/{customerId}")
    ResponseEntity<Customer>editCustomer(@PathVariable Long customerId,
                                         @Validated @RequestBody Customer customer){
        if (customerService.editCustomer(customerId,customer)==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerService.editCustomer(customerId,customer));
    }

}
