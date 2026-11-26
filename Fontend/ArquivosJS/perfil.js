let usuario = JSON.parse(localStorage.getItem('usuarioLogado')) 
localStorage.getItem('token')

//Antes de acessar a tela de perfil, verifica se o usuário está logado
if (!usuario) {
    window.location.href = "/ArquivosHTML/login.html"
} else {
    document.querySelector('span#emailUsuario').textContent = usuario.email || "--"
}

document.getElementById("btnSair").addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado")
    localStorage.removeItem("token")
    window.location.href = "/ArquivosHTML/login.html"
})