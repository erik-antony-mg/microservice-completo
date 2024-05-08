package com.microservices.servicefactura.repository;

import com.microservices.servicefactura.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,Long> {

    List<Factura> findByCustomerId(Long customerId);
    Optional<Factura>findByNumFactura(String numFactura);
}
