package com.test.prueba1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "MiClaveSecretaSuperSeguraDeMasDe32Caracteres123";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
