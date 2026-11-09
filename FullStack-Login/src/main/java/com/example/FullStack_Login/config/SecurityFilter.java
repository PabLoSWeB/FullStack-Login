package com.example.FullStack_Login.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.FullStack_Login.repository.LoginInfoRepository;
import com.example.FullStack_Login.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class SecurityFilter extends OncePerRequestFilter{

    private final TokenService tokenService;
    private final LoginInfoRepository loginInfoRepository;

    public SecurityFilter (TokenService tokenService, LoginInfoRepository loginInfoRepository){
        this.tokenService = tokenService;
        this.loginInfoRepository = loginInfoRepository;
    }

    private String recuperarToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer "))return null; {
            return header.substring(7);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        String token = recuperarToken(request);
        if (token != null) {
            String email = tokenService.validarToken(token);
            if (email != null) {
                loginInfoRepository.findByEmail(email).ifPresent(usuario -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                });
            }
        }
        filterChain.doFilter(request, response);
    }
}
