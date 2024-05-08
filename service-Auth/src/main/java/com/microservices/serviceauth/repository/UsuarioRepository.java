package com.microservices.serviceauth.repository;

import com.microservices.serviceauth.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findUsuarioByEmail(String email);
}
