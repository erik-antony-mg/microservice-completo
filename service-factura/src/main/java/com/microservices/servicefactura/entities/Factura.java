package com.microservices.servicefactura.entities;

import com.microservices.servicefactura.model.CustomerDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facturaId;
    @NotEmpty(message = "el numero de factura no debe estar vacio")
    @Column(unique = true)
    private String numFactura;
    @NotEmpty(message = "la descripcion no debe estar vacio")
    private String description;
    @Column(name = "fecha_creacion")
    private LocalDate createAt;
    private String state;
    @Column(name = "customer_id")
    @NotNull(message = "el del cliente no puede ser nulo")
    private Long customerId;
    @Transient
    private CustomerDto customer;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> itemsFacturas;



    @PrePersist
    private void fechaEstado(){
        this.createAt=LocalDate.now();
    }

}
