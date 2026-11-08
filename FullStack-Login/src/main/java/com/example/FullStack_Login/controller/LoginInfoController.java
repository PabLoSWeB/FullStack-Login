package com.example.FullStack_Login.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FullStack_Login.dto.LoginInfoRequestdto;
import com.example.FullStack_Login.dto.LoginInfoResponsedto;
import com.example.FullStack_Login.entity.LoginInfo;
import com.example.FullStack_Login.service.LoginInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController 
@RequestMapping ("/cadastrar")
public class LoginInfoController {
    
    private final LoginInfoService loginInfoService;
    public LoginInfoController (LoginInfoService loginInfoService) {
        this.loginInfoService = loginInfoService;
    }

    @Operation (
        summary = "Cadastrar novos usuários.",
        description = "Registra informções dos novos usuários (CPF, Número de Telefone, Email e Nome Completo)"
    )

    @ApiResponses ({
        @ApiResponse (
            responseCode = "201",
            description = "Operação concluída com sucesso"
        ),
        @ApiResponse (
            responseCode = "400",
            description = "Dados inválidos"
        ),
        @ApiResponse (
            responseCode = "409",
            description = "CPF ou email já cadastrado"
        )
    })
    @PostMapping("/usuarios")
    public ResponseEntity <LoginInfoResponsedto> salvar (@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessários para cadastro.", required = true) @Valid @RequestBody LoginInfoRequestdto requestdto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loginInfoService.salvar(requestdto));
    }

    @Operation (
        summary = "Buscar informações por id.",
        description = "Busca por um id e informa as informações encontradas."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Id encontrado."
        ),
        @ApiResponse (
            responseCode = "400",
            description = "A requisição possui dados inválidos."
        ),
        @ApiResponse (
            responseCode = "404",
            description = "O id inserido não foi encontrado."
        ) 
    })
    @GetMapping("/buscarId{id}")
    public ResponseEntity<LoginInfo> buscarPorId(@Parameter (description = "Insira um id e verá as informações correspondentes ao id.", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(loginInfoService.buscarPorId(id));
    }
    
    @Operation (
        summary = "Listar usuários.",
        description = "Mostra uma lista completa com as informações de cada usuário cadastrado."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Operação concluída."
        )
    })
    @GetMapping("/listagem")
    public ResponseEntity<List<LoginInfoResponsedto>> listar () {
        return ResponseEntity.status(HttpStatus.OK).body(loginInfoService.listar());
    }
    
    @Operation (
        summary = "Listar página com usuários.",
        description = "Mostra uma página com uma ou mais listas completas de usuários cadastros e suas informações."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Operação concluída."
        )
    })
    @GetMapping("/paginas")
    public ResponseEntity<Page<LoginInfoResponsedto>> listarTodosPaginados(@Parameter(description = "Listagem por paginação: page e size.")  Pageable pageable) {
        return ResponseEntity.ok(loginInfoService.listarTodosPaginados(pageable));
    }
    
    @Operation (
        summary = "Deletar usuários por ID.",
        description = "Deleta usuários por ID e retorna uma mensagem de sucesso."
    )

    @ApiResponses ({
    @ApiResponse (
        responseCode = "204",
        description = "Livro deletado."
    ),
    @ApiResponse (
        responseCode = "400",
        description = "A requisição possui dados inválidos."
    ),
    @ApiResponse (
        responseCode = "404",
        description = "O id inserido não foi encontrado."
    ) 
    })
    @DeleteMapping("/deletar{id}")
    public void deletarPorId( @Parameter (description = "identificador do livro", example = "2") @PathVariable Long id) {
        loginInfoService.deletarPorID(id);
    }
     

    @Operation (
        summary = "Atualizar informações.",
        description = "Quando solicitado, atualiza Cpf e Email."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Atualização concluída."
        ),
        @ApiResponse (
            responseCode = "404",
            description = "A requisição possui dados inválidos."
        ),
        @ApiResponse (
            responseCode = "404",
            description = "O id inserido para a atualização não foi encontrado."
        )
    })
    @PutMapping("atualizar/{id}")
    public ResponseEntity<LoginInfoResponsedto> putAtualizar(@Parameter (description = "Identificador da Pessoa.", example = "1")  @PathVariable Long id, @RequestBody LoginInfoRequestdto requestdto) {
        return ResponseEntity.ok(loginInfoService.putAtualizar(id, requestdto));
    }
}
