package com.example.FullStack_Login.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginInfoRequestdto (
    
    @NotBlank (message = "O CPF é obrigatório!")
    @CPF (message = "CPF inválido.")
    String cpf,
    @NotBlank (message = "O número de telefone é obrrigatório!")
    @Pattern (regexp = "^\\d{10,11}$", message = "O telefone deve ter 10 ou 11 dígitos.")
    String numeroTelefone,
    @NotBlank (message = "O email é obrrigatório!")
    @Email (message = "Email inválido.")
    String email,
    @NotBlank (message = "O nome completo é obrrigatório!")
    String nomeCompleto
) {

    
 
}
