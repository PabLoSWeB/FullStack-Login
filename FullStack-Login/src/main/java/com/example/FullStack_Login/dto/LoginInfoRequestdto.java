package com.example.FullStack_Login.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginInfoRequestdto (
    
    @NotBlank (message = "O CPF é obrigatório!")
    @CPF (message = "CPF inválido.")
    String cpf,
    @NotBlank (message = "O número de telefone é obrigatório!")
    @Pattern (regexp = "^\\d{10,11}$", message = "O telefone deve ter 10 ou 11 dígitos.")
    String numeroTelefone,
    @NotBlank (message = "O email é obrigatório!")
    @Email (message = "Email inválido.")
    String email,
    @NotBlank (message = "O nome completo é obrigatório!")
    String nomeCompleto,
    @NotBlank (message = "A senha é obrigatória!")
    @Pattern (regexp = "^(?=(?:.*\\d){2})(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,72}$", message = "A senha deve ter de 8 a 72 caracteres, com pelo menos 2 dígitos, 1 letra maiúscula e 1 caracter especial")
    String senha
) {

    
 
}
