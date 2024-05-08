package com.microservices.servicefactura.service;

import com.microservices.servicefactura.entities.Factura;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FacturaService {

    List<Factura> getAllFacturas();
    List<Factura> getAllFacturasByCustomerId(Long customerId);
    List<Factura> getAllFacturasByCustomerIdNoCustomer(Long customerId);
    Factura getOneFactura(Long facturaId);
    Optional<Factura> getFacturaByNumFactura(String numFactura);
    Factura createFactura(Factura factura);
    Factura editFactura(Factura factura,Long facturaId);
    Factura deleteFactura(String numFactura);

}
