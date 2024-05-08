package com.microservices.serviceauth.services.impl;

import com.microservices.serviceauth.entities.Rol;
import com.microservices.serviceauth.entities.Usuario;
import com.microservices.serviceauth.entities.UsuarioDto;
import com.microservices.serviceauth.entities.enums.RoleName;
import com.microservices.serviceauth.repository.RolRepository;
import com.microservices.serviceauth.repository.UsuarioRepository;
import com.microservices.serviceauth.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getOneUser(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new RuntimeException("usuario no encontrado"));
    }

    @Override
    public Usuario createUser(UsuarioDto usuarioDTO) {

        Set<Rol> roles = new HashSet<>();
        Optional<Rol> rol=rolRepository.findByRoleName(RoleName.USER);
        if (rol.isEmpty()){
            Rol rolUser= Rol.builder().roleName(RoleName.USER).build();
            roles.add(rolUser);
        }
        rol.ifPresent(roles::add);

        Usuario usuario= Usuario.builder()
                .email(usuarioDTO.getEmail())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .roles(roles)
                .build();

        return usuarioRepository.save(usuario);
    }
}
