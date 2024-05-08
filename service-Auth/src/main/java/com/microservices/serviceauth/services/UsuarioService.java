package com.microservices.serviceauth.services;

import com.microservices.serviceauth.entities.Usuario;
import com.microservices.serviceauth.entities.UsuarioDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {

    List<Usuario> getAllUsers();
    Usuario getOneUser(Long usuarioId);
    Usuario createUser(UsuarioDto usuarioDTO);
}

