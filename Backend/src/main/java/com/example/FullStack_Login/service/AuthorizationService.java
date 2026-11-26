package com.example.FullStack_Login.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.FullStack_Login.repository.LoginInfoRepository;



@Service 
public class AuthorizationService implements UserDetailsService{
    
    private LoginInfoRepository loginInfoRepository;

    public AuthorizationService (LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String emailNormalizado = email.trim().toLowerCase();
        return loginInfoRepository.findByEmail(emailNormalizado).orElseThrow(() -> new UsernameNotFoundException("Credencias inválidas."));
    }
}
