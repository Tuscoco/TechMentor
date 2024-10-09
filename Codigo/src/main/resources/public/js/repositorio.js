// Adicionar event listener para o botão "Adicionar"
document.getElementById('adicionar').addEventListener('click', function() {
    // Capturar os valores dos inputs
    const nome = document.getElementById('nome').value;
    const link = document.getElementById('link').value;

    // Validar se os campos não estão vazios
    if (!nome || !link) {
        alert('Por favor, preencha todos os campos!');
        return;
    }

    // Criar objeto com os dados para enviar
    const dados = {
        nome: nome,
        link: link
    };

    // Enviar os dados para o JSON Server
    fetch('http://localhost:3000/repos', {
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
    .then(data => {
        console.log('Sucesso:', data);
        // Adicionar o novo repositório à lista
        const reposDiv = document.getElementById('repos');
        const newRepo = document.createElement('a');
        newRepo.href = link; // Definindo o link do repositório
        newRepo.className = 'repo'; // Usando className em vez de id
        newRepo.innerHTML = `<p id="nomeRepo">${nome}</p>`; // Você pode manter o ID aqui se precisar
        reposDiv.appendChild(newRepo); // Adiciona o novo repositório à lista
        

        // Limpar os campos de entrada
        document.getElementById('nome').value = '';
        document.getElementById('link').value = '';
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
});

// Função para carregar e exibir os repositórios
function carregarRepositorios() {
    fetch('http://localhost:3000/repos')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição');
            }
            return response.json();
        })
        .then(data => {
            const reposDiv = document.getElementById('repos');
            // Limpa a lista existente antes de adicionar os repositórios
            reposDiv.innerHTML = '';

            // Iterar sobre cada repositório e adicioná-lo à página
            data.forEach(repo => {
                const newRepo = document.createElement('div'); // Usar div para conter o repositório e o botão
                newRepo.className = 'repo'; // Usando className

                // Criar botão de excluir
                const deleteButton = document.createElement('div');
                deleteButton.innerHTML = '<i class="ph-fill ph-trash-simple"></i>'; // Adiciona o ícone
                deleteButton.className = 'delete-btn'; // Adiciona uma classe para estilização, se necessário

                // Evento de clique para excluir o repositório
                deleteButton.addEventListener('click', function() {
                    fetch(`http://localhost:3000/repos/${repo.id}`, {
                        method: 'DELETE'
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Erro na requisição ao excluir');
                        }
                        // Remover o repositório da lista
                        newRepo.remove(); // Remove o elemento da interface
                    })
                    .catch((error) => {
                        console.error('Erro:', error);
                    });
                });

                newRepo.appendChild(deleteButton); // Adiciona o botão ao repositório

                // Criar link do repositório
                const repoLink = document.createElement('a');
                repoLink.href = repo.link;
                repoLink.innerHTML = `<textarea id="nomeRepo">${repo.nome}</textarea>`;
                newRepo.appendChild(repoLink); // Adiciona o link ao repositório
                reposDiv.appendChild(newRepo); // Adiciona o novo repositório à lista
            });
        })
        .catch((error) => {
            console.error('Erro:', error);
        });
}
// Carregar repositórios ao iniciar a página
window.onload = carregarRepositorios;
