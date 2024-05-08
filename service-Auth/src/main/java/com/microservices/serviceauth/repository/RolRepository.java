package com.microservices.serviceauth.repository;

import com.microservices.serviceauth.entities.Rol;
import com.microservices.serviceauth.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByRoleName(RoleName roleName);
}
