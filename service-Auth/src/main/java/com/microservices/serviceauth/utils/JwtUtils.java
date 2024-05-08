package com.microservices.serviceauth.utils;

import com.microservices.serviceauth.exceptions.JwtExceptionCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.private.key}")
    private String keyPrivate;
    @Value("${jwt.duration.token}")
    private String durationToken;


    public String generateToken(String email){
        // Obtener la fecha actual
        Date fechaActual = new Date(System.currentTimeMillis());
        // Formatear la fecha actual al formato "dd/mm/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaActualEnFormato = sdf.format(fechaActual);

        // Obtener la fecha de expiración
        long duracionTokenMillis = Long.parseLong(durationToken);
        Date fechaExpiracion = new Date(System.currentTimeMillis() + duracionTokenMillis);
        // Formatear la fecha de expiración al formato "dd/mm/yyyy"
        String fechaExpiracionEnFormato = sdf.format(fechaExpiracion);

        return Jwts.builder()
                .subject(email)
                .issuedAt(fechaActual)
                .claim("fechaInicio",fechaActualEnFormato)
                .expiration(fechaExpiracion)
                .claim("fechaExpiracion",fechaExpiracionEnFormato)
                .signWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()))
                .compact();
    }



    //validar el token
    public Boolean validarToken(String token) {
        try {
            // Parsear el token
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()))
                    .build()
                    .parseSignedClaims(token);

            // Obtener las claims
            Claims claims = jws.getPayload();

            // Verificar si el token ha expirado
            Date now = new Date();
            if (claims.getExpiration().before(now)) {
                throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT ha caducado");
            }
            return true;
        } catch (SignatureException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "Error de firma en el token JWT");
        } catch (MalformedJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT está mal formado");
        } catch (ExpiredJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El token JWT ha caducado");
        } catch (UnsupportedJwtException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El tipo de token JWT no es compatible");
        } catch (IllegalArgumentException ex) {
            throw new JwtExceptionCustom(HttpStatus.BAD_REQUEST, "El cuerpo del token JWT está vacío");
        }
    }


    //obtener claims
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(keyPrivate.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // obtener un solo claims
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    // obtener el claim username
    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }
}
