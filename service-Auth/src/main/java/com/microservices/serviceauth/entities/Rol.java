package com.microservices.serviceauth.entities;


import com.microservices.serviceauth.entities.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolesId;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

}
