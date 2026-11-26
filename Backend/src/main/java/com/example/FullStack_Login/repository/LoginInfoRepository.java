package com.example.FullStack_Login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FullStack_Login.entity.LoginInfo;



public interface LoginInfoRepository extends JpaRepository <LoginInfo, Long>{
    boolean existsByCpf (String cpf);
    boolean existsByCpfAndIdNot (String cpf, Long id);
    boolean existsByEmail (String email);
    boolean existsByEmailAndIdNot (String email, Long id);
    Optional<LoginInfo> findByEmail(String email);
}
