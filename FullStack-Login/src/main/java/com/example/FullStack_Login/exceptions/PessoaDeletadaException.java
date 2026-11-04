package com.example.FullStack_Login.exceptions;

public class PessoaDeletadaException extends RuntimeException{
    public PessoaDeletadaException (String mensagem) {
        super(mensagem);
    }
}
