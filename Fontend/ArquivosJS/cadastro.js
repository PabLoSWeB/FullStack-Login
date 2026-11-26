
let res, resCpf, resTelefone, resSenha,
valorCpf, valorTelefone, valorEmail, valorNomeC, valorSenha;

//Regex criado para validação de CPF, Telefone e Senha
let cpfRegex = /^\d{11}$/
let telefoneRegex = /^\d{10,11}$/
let senhaRegex = /^(?=(?:.*\d){2})(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,72}$/

let form = document.querySelector('form#formCadastro')



function pegarValores() {
    res = document.querySelector('p#resposta')
    resCpf = document.querySelector('p#erroRegexCpf')
    resTelefone = document.querySelector('p#erroRegexTelefone')
    resSenha = document.querySelector('p#erroRegexSenha')
    valorCpf = document.querySelector('input#inputCpf')
    valorTelefone = document.querySelector('input#inputTelefone') 
    valorEmail = document.querySelector('input#inputEmail')
    valorNomeC = document.querySelector('input#inputNome')
    valorSenha = document.querySelector('input#inputSenha')
}

function validarValores () {
    pegarValores()
    let temErro = false

    // Verifica se os valores são nulos
    res.innerHTML = ''
    resCpf.innerHTML = ''
    resTelefone.innerHTML = ''
    resSenha.innerHTML = ''
    res.style.color = "#b91c1c"
    resCpf.style.color = "#b91c1c"
    resTelefone.style.color = "#b91c1c"
    resSenha.style.color = "#b91c1c"
    

    if (valorCpf.value.trim().length == 0) {
        res.innerHTML = "<p>O <strong>CPF</strong> é obrigatório.</p>"
        temErro = true
    }
    if (valorTelefone.value.trim().length == 0) {
        res.innerHTML += "<p>O <strong>Telefone</strong> é obrigatório.</p>"
        temErro = true
    }
    if (valorEmail.value.trim().length == 0) {
        res.innerHTML += "<p>O <strong>E-mail</strong> é obrigatório.</p>"
        temErro = true
    }
    if (valorNomeC.value.trim().length == 0) {
        res.innerHTML += "<p>O <strong>Nome</strong> é obrigatório.</p>"
        temErro = true
    }
    if (valorSenha.value.trim().length == 0) {
        res.innerHTML += "<p>A <strong>Senha</strong> é obrigatória.</p>"
        temErro = true
    }

    // Verifica se as Entradas seguem a Formatação
    if (cpfRegex.test(valorCpf.value.replace(/\D/g, '')) == false && valorCpf.value.trim().length != 0) {
        resCpf.innerHTML = "<p>O CPF deve <strong>conter 11 dígitos</strong></p>"
        temErro = true
    }
    if (telefoneRegex.test(valorTelefone.value.replace(/\D/g, '')) == false && valorTelefone.value.trim().length != 0){
        resTelefone.innerHTML = "<p>Telefone deve conter <strong>10 ou 11 dígitos</strong></p>"
        temErro = true
    }
    if (senhaRegex.test(valorSenha.value) == false && valorSenha.value.trim().length != 0) {
        resSenha.innerHTML = "<p>A senha deve conter:</br> Mínimo <strong>2 dígitos</strong></br> Pelo menos <strong>1 maiúscula</strong></br> pelo menos 1 caractere <strong>especial(!@#%)</strong></br> Entre 8 e 72 caracteres</p>"
        temErro = true
    }
    
    return !temErro
}



function fazerCadastro () {
    pegarValores()
    res.innerHTML = ''

    const estaValido = validarValores()

    if (estaValido == false) return
    
    fetch("http://localhost:8080/cadastrar/usuarios", {
    method : "post",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify ({
        cpf: valorCpf.value.replace(/\D/g, ''), 
        numeroTelefone: valorTelefone.value.replace(/\D/g, ''),          
        email: valorEmail.value.trim().toLowerCase(),
        nomeCompleto: valorNomeC.value.trim(),
        senha: valorSenha.value
    })
   
}).then(async resposta => {  
    let http = {}
    try {
        http = await resposta.json();
    } catch (e) {
        http = { 
            message: "Erro de rede" 
        }
    }
    switch (resposta.status) {
        case 201:
            res.style.color = "#16a34a"
            res.innerHTML = "<p><strong>Cadastro realizado com sucesso!</strong></p>"
            form.reset()
            break
        case 400:
            res.style.color = "#b91c1c"
            res.innerHTML = `<p>Erro de validação.</p>`
            break;
        case 409:
            res.style.color = "#b91c1c"
            res.innerHTML = `<p>${http.message || "CPF ou E-mail já cadastrado."}</p>`
            break
        default: 
            res.style.color = "#b91c1c"
            res.innerHTML = `<p>Erro ${resposta.status}: ${http.message || "Tente novamente."}</p>` 
    }
})
.catch((erro) => {
    console.error("Erro na rede:", erro)
    res.style.color = "#b91c1c"
    res.innerHTML = "<p>Servidor indisponível.</p>"
})
}


pegarValores()

// Apenas o submit dispara o cadastro
form.addEventListener('submit', (event) => {
    event.preventDefault()
    fazerCadastro()
})
