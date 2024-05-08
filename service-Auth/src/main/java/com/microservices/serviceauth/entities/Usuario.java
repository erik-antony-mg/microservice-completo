package com.microservices.serviceauth.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuarios_roles",joinColumns = @JoinColumn(name = "usuario_id")
            ,inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles;

}
