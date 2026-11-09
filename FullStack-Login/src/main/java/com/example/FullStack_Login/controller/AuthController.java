package com.example.FullStack_Login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FullStack_Login.dto.LoginRequestdto;
import com.example.FullStack_Login.dto.LoginResponsedto;
import com.example.FullStack_Login.entity.LoginInfo;
import com.example.FullStack_Login.service.TokenService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping ("/auth")
public class AuthController{
    
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthController (AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponsedto> login(@Valid @RequestBody LoginRequestdto requestdto) {
    
        UsernamePasswordAuthenticationToken credenciais = new UsernamePasswordAuthenticationToken(requestdto.email().translateEscapes().toLowerCase(), requestdto.senha());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        String token = tokenService.gerarToken((LoginInfo) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponsedto(token));
    }
    
}
