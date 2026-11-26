let res, valorEmail, valorSenha, resSenha

//Regex criado para validação da Senha
let senhaRegex = /^(?=(?:.*\d){2})(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,72}$/

let form = document.querySelector('form#formCadastro')


function pegarValores () {
    valorEmail = document.querySelector('input#inputEmail')
    valorSenha = document.querySelector('input#inputSenha')
    res = document.querySelector('p#resposta')
    resSenha = document.querySelector('p#erroRegexSenha')
}

function validarValores () {
    pegarValores()
    let temErro = false
    //Verifica se há valores nulos
    res.innerHTML = ''
    resSenha.innerHTML = ''
    res.style.color = "#b91c1c"
    resSenha.style.color = "#b91c1c"
    if (valorEmail.value.trim().length == 0 ) {
        res.innerHTML = '<p>O <strong>E-mail</strong> é obrigatório!</p>'
        temErro = true
    }
    if (valorSenha.value.trim().length == 0) {
        res.innerHTML += '<p>A <strong>senha</strong> é obrigatória!</p>'
    }

    //Verifica se a Senha segue a formatação 
    if (senhaRegex.test(valorSenha.value) == false && valorSenha.value.trim().length != 0) {
        resSenha.innerHTML = "<p>A senha deve conter:</br> Mínimo <strong>2 dígitos</strong></br> Pelo menos <strong>1 maiúscula</strong></br> pelo menos 1 caractere <strong>especial(!@#%)</strong></br> Entre 8 e 72 caracteres</p>"
        temErro = true
    }

    return !temErro
}

function fazerLogin () {
    pegarValores()
    const estaValido = validarValores()

    if (estaValido == false) return
    fetch ("http://localhost:8080/auth/login", {
        method: 'post',
        headers: {
            "Content-Type" : "application/json"
        },

        body: JSON.stringify ({

            email: valorEmail.value.trim().toLowerCase(),
            senha: valorSenha.value

        })
    }).then (async resposta => {
        let http = {}
        try {
            http = await resposta.json()
        } catch (e) {
            http = {
                message: "Erro de rede"
            }
        }

        switch (resposta.status) {
            case 200:
                res.style.color = "#16a34a"
                res.innerHTML = "<p><strong>Login realizado com sucesso!</strong></p>"
                localStorage.setItem('usuarioLogado', JSON.stringify({
                    email: valorEmail.value.trim().toLowerCase()
                    }))
                form.reset()

                if(http.token) {
                    localStorage.setItem('token', http.token)
                    setTimeout(() => {
                        location.href = '../ArquivosHTML/perfil.html'
                    }, 1000)

                } else {
                    res.style.color = "#b91c1c"
                    res.innerHTML = "<p>Erro: Token não recebido.</p>";
                }
                break

            case 400: 
            case 401:
            case 404:
                res.style.color = "#b91c1c"
                res.innerHTML = "<p>Credenciais inválidas.</p>"
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

// Apenas o submit dispara o login
form.addEventListener('submit', (event) => {
    event.preventDefault()
    fazerLogin()
})