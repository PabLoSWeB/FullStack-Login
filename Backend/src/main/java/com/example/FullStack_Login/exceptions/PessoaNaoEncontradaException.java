package com.example.FullStack_Login.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException{
    
    public PessoaNaoEncontradaException (String mensagem) {
        super(mensagem);
    }
}
