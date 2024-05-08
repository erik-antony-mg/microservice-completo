package com.microservices.serviceauth.services.impl;

import com.microservices.serviceauth.entities.Usuario;
import com.microservices.serviceauth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario=usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("usuario con el "+email+" no encontrado"));

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList=new ArrayList<>();
        usuario.getRoles()
                .forEach(rol -> simpleGrantedAuthorityList
                        .add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleName().name()))));
        return new User(usuario.getEmail(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                simpleGrantedAuthorityList);
    }
}