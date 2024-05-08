package com.microservices.servicefactura.configuration.client;

import com.microservices.servicefactura.model.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductWebClientImpl implements ProductWebClient{

    @Autowired
    @Qualifier("product")
    private WebClient webClientProduct;

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetOneProduct")
    @Override
    public ProductDto getOneProduct(String productId) {
        return webClientProduct
                .get()
                .uri("/api/products/{productId}",productId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    @Override
    public ProductDto updateStock(String productId, double quantity) {
        return webClientProduct
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/products/update/stock/{productId}")
                        .queryParam("stock",quantity)
                        .build(productId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    public ProductDto fallbackGetOneProduct(String productId,Exception e) {
        return null;
    }
}
