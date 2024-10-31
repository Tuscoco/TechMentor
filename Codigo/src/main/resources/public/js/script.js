const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));

if (usuarioLogado) {
    inicializarUsuario(usuarioLogado); // Chama a função para iniciar
} else {
    // Redireciona para login se o usuário não estiver logado
    alert('Faça login para acessar a página.');
    window.location.href = '../html/logCad.html'; // Ajuste para a URL correta da página de login
}

async function inicializarUsuario(usuarioLogado) {
    try {

        // Atualiza o sessionStorage com o objeto atualizado
        sessionStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogado));
        
        document.querySelector('#username').textContent = usuarioLogado.nome;

        console.log(usuarioLogado);
        console.log(`Bem-vindo, ${usuarioLogado.nome}!`);

        // Exibe o nome no elemento com ID 'username'
    } catch (error) {
        console.error('Erro ao inicializar o usuário:', error);
    }
}

