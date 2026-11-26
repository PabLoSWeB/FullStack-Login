package com.example.FullStack_Login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestdto(

    @NotBlank (message = "O email é obrigatório!")
    @Email (message = "Email inválido.")
    String email,
    @NotBlank (message = "A senha é obrigatória!")
    String senha

) {
    
}
