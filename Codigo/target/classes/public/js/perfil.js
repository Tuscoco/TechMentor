const usuarioLogadoPerfil = JSON.parse(sessionStorage.getItem('usuarioLogado'));


document.querySelector('#nameEdit').textContent = usuarioLogadoPerfil.nome;
document.querySelector('#senhaEdit').textContent = usuarioLogadoPerfil.senha;

document.getElementById('sair').addEventListener('click', function() {
    
    localStorage.clear();
    sessionStorage.clear();
    
    window.location.reload();
    window.location.href = 'logCad.html'; 
});
