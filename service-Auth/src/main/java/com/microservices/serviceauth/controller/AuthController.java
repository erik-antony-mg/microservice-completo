package com.microservices.serviceauth.controller;

import com.microservices.serviceauth.entities.UsuarioDto;
import com.microservices.serviceauth.services.UsuarioService;
import com.microservices.serviceauth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsuarios(){
        return ResponseEntity.ok(usuarioService.getAllUsers());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> getOneUser(@PathVariable Long usuarioId){
        return ResponseEntity.ok(usuarioService.getOneUser(usuarioId));
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDto usuarioDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(usuarioDTO));
    }
    @GetMapping("/validate")
    public ResponseEntity<?>validarToken(@RequestParam String token){
        Map<String,String> resp=new HashMap<>();
        if (jwtUtils.validarToken(token)){
            resp.put("respuesta","token valido");
            return ResponseEntity.ok(resp);
        }
        resp.put("respuesta","token no valido");
        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST) ;
    }
    @GetMapping("/principal")
    @PreAuthorize("hasRole('ADMIN')")
    public Object principal(){
        return SecurityContextHolder.getContext();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UsuarioDto authRequestDto){
        String token;
        String email;
        Authentication authentication   = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            Map<String,Object> resp=new HashMap<>();
            token= jwtUtils.generateToken(authRequestDto.getEmail());
            email=jwtUtils.getUsername(token);
            resp.put("mensaje","el Usuario "+email+" ha iniciado con exito!");
            resp.put("token",token);
            return ResponseEntity.ok(resp);
        }
        else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }
}
