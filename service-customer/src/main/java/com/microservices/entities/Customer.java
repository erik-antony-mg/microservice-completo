package com.microservices.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(unique = true)
    @NotEmpty(message = "El dni no puede ser vacio")
    private String dni;
    @NotEmpty(message = "El nombre no puede ser vacio")
    private String firstName;
    @NotEmpty(message = "El apellido no puede ser vacio")
    private String lastName;
    @Column(unique = true)
    @Email(message = "escriba en este formato @example.com")
    @NotEmpty(message = "EL email no puede estar vacio")
    private String email;
    private String photoUrl;
    private String state;
    @ManyToOne
    @JoinColumn(name = "region_id")
    @NotNull(message = "la region no puede estar vacia")
    private Region region;

}
