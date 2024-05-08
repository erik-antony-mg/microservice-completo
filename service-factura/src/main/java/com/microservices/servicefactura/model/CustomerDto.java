package com.microservices.servicefactura.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Long customerId;
    private String dni;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String state;
    private RegionDto region;
}
