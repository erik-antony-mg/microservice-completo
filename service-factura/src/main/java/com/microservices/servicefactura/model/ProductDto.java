package com.microservices.servicefactura.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductDto {
    String productId;
    String name;
    String description;
    String status;
    Double price;
    Double stock;
    CategoryDto category;
}
