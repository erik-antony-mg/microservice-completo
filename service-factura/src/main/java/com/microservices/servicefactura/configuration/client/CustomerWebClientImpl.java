package com.microservices.servicefactura.configuration.client;

import com.microservices.servicefactura.model.CustomerDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CustomerWebClientImpl implements CustomerWebClient{

    @Autowired
    @Qualifier("customer")
    private  WebClient webClientCustomer;

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackForGetOneCustomeById")
    @Override
    public CustomerDto getOneCustomeById(Long customerId) {

            return webClientCustomer
                    .get()
                    .uri("/api/customer/{customerId}", String.valueOf(customerId))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(CustomerDto.class)
                    .block();
    }
    public CustomerDto fallbackForGetOneCustomeById(Long customerId, Exception e) {
        return null; // Manejo de fallback en caso de fallo
    }
}
