package com.microservices.servicefactura.controller;

import com.microservices.servicefactura.entities.Factura;
import com.microservices.servicefactura.service.FacturaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/factura")
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping("/list")
//    @CircuitBreaker(name = "customerService",fallbackMethod = "fallbackCustomer")
    ResponseEntity<List<Factura>> getAllFacturas(@RequestParam(required = false) Long customerId){

        if (customerId==null){
            if (facturaService.getAllFacturas().isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(facturaService.getAllFacturas());
        }
        else {
            if (facturaService.getAllFacturasByCustomerId(customerId)==null){
                return ResponseEntity.notFound().build();
            }else {
                if (facturaService.getAllFacturasByCustomerId(customerId).isEmpty()){
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(facturaService.getAllFacturasByCustomerId(customerId));
            }
        }
    }
    @GetMapping("/{numFactura}")
    ResponseEntity<Factura> getOneFactura(@PathVariable String numFactura){
        Optional<Factura> facturaBd= facturaService.getFacturaByNumFactura(numFactura.toLowerCase());
        return facturaBd.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    ResponseEntity<Factura> createFactura(@Validated @RequestBody Factura factura){
       return new ResponseEntity<>(facturaService.createFactura(factura),HttpStatus.CREATED);
    }

    @PutMapping("/edit/{facturaId}")
    ResponseEntity<Factura> editProduct(@Validated @RequestBody Factura factura ,@PathVariable Long facturaId){
        if (facturaService.editFactura(factura,facturaId)==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facturaService.editFactura(factura,facturaId));
    }

    @DeleteMapping("/delete/{numFactura}")
    ResponseEntity<Factura> deleteFactura(@PathVariable String numFactura){
        return new ResponseEntity<>(facturaService.deleteFactura(numFactura),HttpStatus.ACCEPTED);
    }


//    public ResponseEntity<List<Factura>> fallbackCustomer(@RequestParam(required = false) Long customerId,Exception exception) {
//
//            if (customerId == null) {
//                List<Factura> facturas = facturaService.getAllFacturas();
//                facturas.forEach(factura -> factura.setCustomer(null));
//                return ResponseEntity.ok(facturas);
//            } else {
//                List<Factura> facturas = facturaService.getAllFacturasByCustomerIdNoCustomer(customerId);
//                if (facturas != null) {
//                    facturas.forEach(factura -> factura.setCustomer(null));
//                    return ResponseEntity.ok(facturas);
//                } else {
//                    return ResponseEntity.notFound().build();
//                }
//            }
//
//    }


}
