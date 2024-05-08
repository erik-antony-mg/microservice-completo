package com.microservices.servicefactura.configuration.client;

import com.microservices.servicefactura.model.ProductDto;

public interface ProductWebClient {
    ProductDto getOneProduct(String productId);
    ProductDto updateStock(String productId,double quantity);
}
