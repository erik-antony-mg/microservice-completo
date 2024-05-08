package com.microservices.servicefactura.entities;

import com.microservices.servicefactura.model.ProductDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Table(name = "item_facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemFacturaId;
    @Positive(message = "la Cantidad debe ser mayor a cero")
    @NotNull(message = "la cantidad no debe ser nulo")
    private Integer quantity;
    @Positive(message = "el precio debe ser mayor a cero")
    @NotNull(message = "el precio no debe ser nulo")
    private Double price;
    @Column(name = "product_id")
    @NotNull(message = "el id del producto no debe ser nulo")
    private String productId;
    @Transient
    private ProductDto product;
    @Transient
    private Double subTotal;

    public Double getSubTotal() {
        if (quantity != null && price != null && quantity > 0 && price > 0) {
            return this.quantity * this.price;
        } else {
            return 0.0;
        }
    }
}
