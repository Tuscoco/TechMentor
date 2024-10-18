// Adicionar event listener para o botão "Adicionar"
document.getElementById('adicionar').addEventListener('click', function() {
    // Capturar os valores dos inputs
    const nome = document.getElementById('nome').value;
    const link = document.getElementById('link').value;
    const id = 0;

    // Validar se os campos não estão vazios
    if (!nome || !link) {
        alert('Por favor, preencha todos os campos!');
        return;
    }

    // Criar objeto com os dados para enviar
    const dados = {
        id: id,
        nome: nome,
        link: link
    };

    console.log(dados);

    // Enviar os dados para o JSON Server
    fetch('http://localhost:4567/salvarrepositorio', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        return response.json();
    })
    .then((data) => {
        console.log('Sucesso:', data);
        const reposDiv = document.getElementById('repos');

        const newRepo = document.createElement('a');
        newRepo.href = link;
        newRepo.target = '_blank'; // Abre o link em uma nova aba
        newRepo.rel = 'noopener noreferrer'; // Evita vulnerabilidades de segurança
        newRepo.className = 'repo';
        newRepo.innerHTML = `<p id="nomeRepo">${nome}</p>`;
        reposDiv.appendChild(newRepo);

        document.getElementById('nome').value = '';
        document.getElementById('link').value = '';

        // Recarregar os repositórios após adicionar um novo
        carregarRepositorios();
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
});

// Função para carregar e exibir os repositórios
function carregarRepositorios() {
    fetch('http://localhost:4567/mostrarrepositorio', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json' // Define o tipo de conteúdo como JSON
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        return response.json(); // Converte a resposta para JSON
    })
    .then(data => {
        const reposDiv = document.getElementById('repos');
        reposDiv.innerHTML = ''; // Limpa a lista antes de carregar novos dados

        if (data.length === 0) {
            reposDiv.innerHTML = '<p>Nenhum repositório encontrado.</p>';
            return;
        }

        data.forEach(repo => {
            const newRepo = document.createElement('div');
            newRepo.className = 'repo';

            // Criar botão de excluir
            const deleteButton = document.createElement('div');
            deleteButton.innerHTML = '<i class="ph-fill ph-trash-simple"></i>';
            deleteButton.className = 'delete-btn';

            // Evento de clique para excluir o repositório
            deleteButton.addEventListener('click', function() {
                fetch(`http://localhost:4567/deletarrepositorio/${repo.id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json' // Cabeçalho opcional para DELETE
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na requisição ao excluir');
                    }
                    newRepo.remove(); // Remove o repositório da interface
                })
                .catch((error) => {
                    console.error('Erro ao excluir:', error);
                });
            });

            newRepo.appendChild(deleteButton);

            // Criar link do repositório
            const repoLink = document.createElement('a');
            repoLink.href = repo.link;
            repoLink.innerHTML = `<div class="nome">${repo.nome}</div>`;
            newRepo.appendChild(repoLink);

            reposDiv.appendChild(newRepo);
        });
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
}

// Carregar repositórios ao iniciar a página
window.onload = carregarRepositorios;