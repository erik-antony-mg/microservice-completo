package com.microservices.servicefactura.service.impl;

import com.microservices.servicefactura.configuration.client.CustomerWebClient;
import com.microservices.servicefactura.configuration.client.ProductWebClient;
import com.microservices.servicefactura.entities.Factura;
import com.microservices.servicefactura.entities.ItemFactura;
import com.microservices.servicefactura.exceptions.FacturaNotFound;
import com.microservices.servicefactura.model.CustomerDto;
import com.microservices.servicefactura.model.ProductDto;
import com.microservices.servicefactura.repository.FacturaRepository;
import com.microservices.servicefactura.repository.ItemFacturaRepository;
import com.microservices.servicefactura.service.FacturaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {
    private  final FacturaRepository facturaRepository;
    private final ItemFacturaRepository itemFacturaRepository;
    private final CustomerWebClient customerWebClient;
    private final ProductWebClient productWebClient;

    @Override
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "getAllFacturasByCustomerId")
    @Override
    public List<Factura> getAllFacturasByCustomerId(Long customerId) {
        CustomerDto customerDto=customerWebClient.getOneCustomeById(customerId);

        List<Factura> facturas = facturaRepository.findByCustomerId(customerId);
        facturas.forEach(factura -> {
            factura.setCustomer(customerDto);
            factura.getItemsFacturas().forEach(itemFactura ->
                itemFactura.setProduct(productWebClient.getOneProduct(itemFactura.getProductId()))
            );
        });
        return facturas;
    }

    @Override
    public List<Factura> getAllFacturasByCustomerIdNoCustomer(Long customerId) {
        List<Factura> facturas = facturaRepository.findByCustomerId(customerId);
        facturas.forEach(factura -> {
            factura.getItemsFacturas().forEach(itemFactura ->
                    itemFactura.setProduct(productWebClient.getOneProduct(itemFactura.getProductId()))
            );
        });
        return facturas;
    }

    @Override
    public Factura getOneFactura(Long facturaId) {
        return facturaRepository.findById(facturaId)
                .orElseThrow(()-> new FacturaNotFound("no se encontro la factura"));
    }

    @Override
    public Optional<Factura> getFacturaByNumFactura(String numFactura) {
        return facturaRepository.findByNumFactura(numFactura);
    }

    @Override
    public Factura createFactura(Factura factura) {
        Optional<Factura> facturaOptional= facturaRepository
                .findByNumFactura(factura.getNumFactura().toLowerCase());
        if(facturaOptional.isPresent()){
            return facturaOptional.get();
        }
        factura.setState("CREATED");
        CustomerDto customerDto=customerWebClient.getOneCustomeById(factura.getCustomerId());
        factura.setCustomer(customerDto);
        List<ItemFactura> itemFacturaList= new ArrayList<>();
        factura.getItemsFacturas().forEach(itemFactura -> {
            ProductDto productDto = productWebClient.getOneProduct(itemFactura.getProductId());
            ProductDto productDtoStock= productWebClient
                    .updateStock(itemFactura.getProductId(), productDto.getStock() - 1);
            itemFacturaList.add(itemFactura);
            itemFactura.setProduct(productDtoStock);
            itemFacturaRepository.save(itemFactura);
        });

        factura.setItemsFacturas(itemFacturaList);

        return facturaRepository.save(factura);
    }

    @Override
    public Factura editFactura(Factura factura, Long facturaId) {
        Optional<Factura> facturaOptional= facturaRepository.findById(facturaId);
        if (facturaOptional.isPresent()){
            facturaOptional.get().setNumFactura(factura.getNumFactura());
            facturaOptional.get().setDescription(factura.getDescription());
            facturaOptional.get().setCustomerId(factura.getCustomerId());

            List<ItemFactura> itemFacturaList= factura.getItemsFacturas().stream()
                    .map(itemFacturaRepository::save).toList();

            facturaOptional.get().getItemsFacturas().forEach(itemFactura ->
                itemFacturaRepository.deleteById(itemFactura.getItemFacturaId())
            );

            facturaOptional.get().setItemsFacturas(itemFacturaList);
            return facturaRepository.save(facturaOptional.get());
        }
        return null;
    }

    @Override
    public Factura deleteFactura(String numFactura) {
        Factura facturaDb= facturaRepository.findByNumFactura(numFactura)
                .orElseThrow(()-> new FacturaNotFound("no se encontro la factura"));
        facturaDb.setState("DELETE");
        return facturaRepository.save(facturaDb);
    }
}
