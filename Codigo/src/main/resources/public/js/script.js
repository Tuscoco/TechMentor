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
        
        const id = usuarioLogado.id;
        
        // Aguarda a resposta das funções assíncronas
        usuarioLogado.tipo = await getTipoUsuario(id);
        usuarioLogado.nome = await getNomeUsuario(id);
        
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

async function getTipoUsuario(id) {
    try {
        const response = await fetch(`http://localhost:4567/tipousuario/${id}`, { 
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (response.ok) {
            const result = await response.json();
            return result; // Retorna o tipo de usuário (0, 1, 2 ou 3)
        } else if (response.status === 404) {
            alert("Pessoa não encontrada");
            return null;
        } else {
            console.error('Erro ao obter o tipo de usuário:', response.status);
            alert('Erro ao obter o tipo de usuário: ' + response.status);
            return null;
        }
    } catch (error) {
        console.error('Erro ao realizar a requisição:', error);
        alert('Erro ao realizar a requisição');
        return null;
    }
}

async function getNomeUsuario(id) {
    try {
        const response = await fetch(`http://localhost:4567/mostrarnome/${id}`, { 
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (response.ok) {
            const result = await response.json();
            return result; // Retorna o nome do usuário
        } else if (response.status === 404) {
            alert("Pessoa não encontrada");
            return null;
        } else {
            console.error('Erro ao obter o nome do usuário:', response.status);
            alert('Erro ao obter o nome do usuário: ' + response.status);
            return null;
        }
    } catch (error) {
        console.error('Erro ao realizar a requisição:', error);
        alert('Erro ao realizar a requisição');
        return null;
    }
}