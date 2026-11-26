package com.example.FullStack_Login.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.FullStack_Login.entity.LoginInfo;

@Component 
@Service
public class TokenService {
    
    @Value ("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "fullstack-login";

    public String gerarToken (LoginInfo loginInfo) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(loginInfo.getEmail())
        .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
        .sign(algorithm);
    }

    public String validarToken (String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }    
    }
}
