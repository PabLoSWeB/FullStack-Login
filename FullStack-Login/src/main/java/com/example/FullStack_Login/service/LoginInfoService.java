package com.example.FullStack_Login.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FullStack_Login.dto.LoginInfoRequestdto;
import com.example.FullStack_Login.dto.LoginInfoResponsedto;
import com.example.FullStack_Login.entity.LoginInfo;
import com.example.FullStack_Login.exceptions.CpfJaCadastradoException;
import com.example.FullStack_Login.exceptions.EmailJaCadastradoException;
import com.example.FullStack_Login.exceptions.PessoaDeletadaException;
import com.example.FullStack_Login.exceptions.PessoaNaoEncontradaException;
import com.example.FullStack_Login.repository.LoginInfoRepository;



@Service 
public class LoginInfoService {
    private  LoginInfoRepository loginInfoRepository;

    public LoginInfoService (LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }

    
    private LoginInfoResponsedto converterParaDto (LoginInfo loginInfo) {
        return new LoginInfoResponsedto (
            loginInfo.getId(),
            loginInfo.getEmail(),
            loginInfo.getNumeroTelefone(),
            loginInfo.getNomeCompleto()
        );
    }

    private String somenteDigitos (String valor) {
        return valor.replaceAll("\\D", " ");
    }

    @Transactional
    public LoginInfoResponsedto salvar (LoginInfoRequestdto requestdto) {
        String cpfNormalizado = somenteDigitos(requestdto.cpf());
        String numeroTelefoneNormalizado = somenteDigitos(requestdto.numeroTelefone());
        String emailNormalizado = requestdto.email().trim().toLowerCase();
        String nomeCompletoNormalizado = requestdto.nomeCompleto().trim();
        if (loginInfoRepository.existsByCpf(cpfNormalizado)) {
            throw new CpfJaCadastradoException("Este cpf já está cadastrado.");
        }
        if (loginInfoRepository.existsByEmail(emailNormalizado)) {
            throw new EmailJaCadastradoException("Este email já está cadastrado.");
        }
        
        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setCpf(cpfNormalizado);
        loginInfo.setNumeroTelefone(numeroTelefoneNormalizado);
        loginInfo.setEmail(emailNormalizado);
        loginInfo.setNomeCompleto(nomeCompletoNormalizado);

        LoginInfo salvo = loginInfoRepository.save(loginInfo);
        return converterParaDto(salvo);
         
    }

    @Transactional (readOnly = true)
    public LoginInfo buscarPorId (Long id) {
        return loginInfoRepository.findById(id).orElseThrow(()-> new PessoaNaoEncontradaException("As informações buscadas pelo id" +id+ "não foram encontradas."));  
    }
    
    @Transactional (readOnly = true)
    public List<LoginInfoResponsedto> listar () {
        return loginInfoRepository.findAll().stream().map(this::converterParaDto).toList();
    }

    @Transactional (readOnly = true)
    public Page<LoginInfoResponsedto> listarTodosPaginados (Pageable pageable) {
        return loginInfoRepository.findAll(pageable).map(this::converterParaDto);
    }

    public void deletarPorID (Long id) {
        if (!loginInfoRepository.existsById(id)) {
            throw new PessoaDeletadaException("A pessoa cadastrado com id "+id+" não foi encontrada.");
        }
        loginInfoRepository.deleteById(id);
    }

    @Transactional
    public LoginInfoResponsedto putAtualizar (Long id, LoginInfoRequestdto requestdto) {
        String cpfNormalizado = somenteDigitos(requestdto.cpf());
        String numeroTelefoneNormalizado = somenteDigitos(requestdto.numeroTelefone());
        String emailNormalizado = requestdto.email().trim().toLowerCase();
        String nomeCompletoNormalizado = requestdto.nomeCompleto().trim();
        LoginInfo login = buscarPorId(id);

        if (loginInfoRepository.existsByCpfAndIdNot(cpfNormalizado, id)) {
            throw new CpfJaCadastradoException("Este cpf já está cadastrado.");
        }
        if (loginInfoRepository.existsByEmailAndIdNot(emailNormalizado, id)) {
            throw new EmailJaCadastradoException("Este email já está cadastrado.");
        }   
        
        login.setCpf(cpfNormalizado);
        login.setNumeroTelefone(numeroTelefoneNormalizado);
        login.setEmail(emailNormalizado);
        login.setNomeCompleto(nomeCompletoNormalizado);
        loginInfoRepository.save(login);
        return converterParaDto(login);
    }
}
