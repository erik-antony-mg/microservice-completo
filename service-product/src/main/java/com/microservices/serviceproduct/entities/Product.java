package com.microservices.serviceproduct.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;


@Document(collection = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    String productId;
    @NotEmpty(message = "El nombre no debe ser vacio")
    String name;
    String description;
    String status;
    @Positive(message = "El precio debe ser mayor a cero")
    Double price;
    @Positive(message = "El stock debe ser mayor a cero")
    Double stock;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate create_at;
    @DBRef
    @NotNull(message = "La categoria no puede ser vacia")
    Category category;

}
