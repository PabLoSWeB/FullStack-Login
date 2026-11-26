# FullStack-Login

Sistema de cadastro e login com **API REST em Spring Boot** protegida por **Spring Security + JWT** e um **front-end em HTML, CSS e JavaScript** que consome a API com `fetch`.

Projeto de estudo para praticar o caminho completo de uma aplicação: validação de dados, tratamento de erros, autenticação stateless, documentação da API e integração com o front.

## Funcionalidades

- Cadastro de usuário com validação de CPF, telefone, e-mail, nome e senha
- Login com e-mail e senha, retornando um token JWT (validade de 2 horas)
- Rotas protegidas por token (`Authorization: Bearer <token>`)
- Senha armazenada com hash BCrypt; nunca retorna em respostas
- Respostas de erro padronizadas (400, 401, 403, 404, 409) em JSON
- Documentação interativa com Swagger UI e botão **Authorize**
- Front com telas de cadastro, login e perfil

## Tecnologias

**Back-end:** Java 17, Spring Boot 4.1, Spring Web MVC, Spring Data JPA, Spring Security 7, Bean Validation, java-jwt (Auth0), springdoc-openapi (Swagger UI), Lombok, H2 (banco em memória), Maven

**Front-end:** HTML, CSS e JavaScript puro (DOM, `fetch`, `localStorage`)

## Estrutura do repositório

```
FullStack-Login/
├── Backend/      API Spring Boot
└── Frontend/     Telas em HTML, CSS e JS
```

Organização do back-end em camadas:

```
com.example.FullStack_Login
├── controller/   endpoints REST
├── service/      regras de negócio, TokenService, AuthorizationService
├── repository/   acesso a dados (Spring Data JPA)
├── entity/       entidade LoginInfo (implementa UserDetails)
├── dto/          records de entrada e saída
├── exceptions/   exceções customizadas + GlobalExceptionHandler
└── config/       SecurityConfig, SecurityFilter, OpenApiConfig
```

## Como a autenticação funciona

1. `POST /cadastrar/usuarios` cria o usuário e grava a senha como hash BCrypt.
2. `POST /auth/login` confere e-mail e senha e devolve um JWT assinado (HMAC256).
3. O front guarda o token e o envia no header `Authorization` das próximas chamadas.
4. O `SecurityFilter` valida o token a cada requisição e identifica o usuário.
5. Sem token válido, a API responde 401 em JSON. Sem permissão, 403.

A API é *stateless*: não mantém sessão no servidor.

## Endpoints

| Método | Rota | Acesso | Descrição |
|--------|------|--------|-----------|
| POST | `/cadastrar/usuarios` | Público | Cadastra um usuário |
| POST | `/auth/login` | Público | Autentica e retorna o token |
| GET | `/cadastrar/buscarId{id}` | Token | Busca usuário por id |
| GET | `/cadastrar/listagem` | Token | Lista todos os usuários |
| GET | `/cadastrar/paginas` | Token | Lista paginada (`page`, `size`, `sort`) |
| PUT | `/cadastrar/atualizar/{id}` | Token | Atualiza CPF, telefone, e-mail e nome |
| DELETE | `/cadastrar/deletar{id}` | Token | Remove um usuário |

A documentação completa fica no Swagger UI: `http://localhost:8080/swagger-ui.html`

## Como executar

### Pré-requisitos

- Java 17
- Visual Studio Code com a extensão Live Server (ou outro servidor estático na porta 5500)

### Back-end

```bash
cd Backend
./mvnw spring-boot:run        # no Windows: .\mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

O segredo do JWT é lido da variável de ambiente `JWT_SECRET`. Se ela não estiver definida, é usado um valor de desenvolvimento definido em `application.properties`. **Em qualquer uso real, defina um segredo próprio com 32 ou mais caracteres.**

### Front-end

1. Abra a pasta `Frontend` no VS Code.
2. Inicie o Live Server (porta 5500).
3. Acesse `http://127.0.0.1:5500/ArquivosHTML/login.html`.

O CORS do back-end libera as origens `http://localhost:5500` e `http://127.0.0.1:5500`.

### Testando sem o front

O arquivo `Backend/teste.http` tem exemplos de cadastro, login, acesso com token, token alterado e token expirado (extensão REST Client do VS Code). Pelo Swagger, faça o login, copie o token e use o botão **Authorize**.

## Regras de validação

| Campo | Regra |
|-------|-------|
| CPF | Obrigatório e válido (`@CPF`); salvo apenas com dígitos |
| Telefone | 10 ou 11 dígitos |
| E-mail | Formato válido; salvo em minúsculas e sem espaços nas pontas |
| Nome completo | Obrigatório |
| Senha | 8 a 72 caracteres, com ao menos 2 dígitos, 1 letra maiúscula e 1 caractere especial |

## Limitações conhecidas e próximos passos

- **Banco em memória (H2):** os dados são perdidos ao reiniciar. Próximo passo: PostgreSQL ou SQL Server.
- **Sem controle de acesso por perfil:** todo usuário autenticado tem o papel fixo `ROLE_USER`, e qualquer usuário autenticado consegue listar, editar e remover outros. Próximo passo: roles e regras de autorização por dono do dado.
- **Tela de perfil básica** e tela "Modificar informações" ainda em construção.
- Sem testes automatizados.

## Sobre o desenvolvimento

Projeto desenvolvido por mim como estudo de Spring Boot e Spring Security. Usei IA como apoio em dois pontos: depuração/identificação de erros e a criação da tela de perfil. O restante foi escrito e testado por mim.

## Autor

**Pablo Ribeiro**
[LinkedIn](https://www.linkedin.com/in/pablo-ribeiro-3794b1379) · [GitHub](https://github.com/PabLoSWeB)
