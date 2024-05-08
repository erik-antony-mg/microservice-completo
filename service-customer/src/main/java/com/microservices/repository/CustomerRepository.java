package com.microservices.repository;

import com.microservices.entities.Customer;
import com.microservices.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long > {

    Optional<Customer>  findByDni(String dni);
    List<Customer> findByRegion(Region region);
}
