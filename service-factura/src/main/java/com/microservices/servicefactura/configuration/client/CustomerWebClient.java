package com.microservices.servicefactura.configuration.client;

import com.microservices.servicefactura.model.CustomerDto;

public interface CustomerWebClient {
    CustomerDto getOneCustomeById(Long customerId);
}
