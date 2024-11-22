const url = 'http://localhost:4567'; // Endereço do seu servidor

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
    fetch(`${url}/salvarrepositorio`, {
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
    fetch(`${url}/mostrarrepositorio`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json' 
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        return response.json(); 
    })
    .then(data => {
        const reposDiv = document.getElementById('repos');
        reposDiv.innerHTML = ''; 

        if (data.length === 0) {
            reposDiv.innerHTML = '<p>Nenhum repositório encontrado.</p>';
            return;
        }

        data.forEach(repo => {
            const newRepo = document.createElement('div');
            newRepo.className = 'repo';

            const deleteButton = document.createElement('div');
            deleteButton.innerHTML = '<i class="ph-fill ph-trash-simple"></i>';
            deleteButton.className = 'deleteBtn';

            deleteButton.addEventListener('click', function() {
                fetch(`${url}/deletarrepositorio/${repo.id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json' 
                    }
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na requisição ao excluir');
                    }
                    newRepo.remove();
                })
                .catch((error) => {
                    console.error('Erro ao excluir:', error);
                });
            });

            newRepo.appendChild(deleteButton);

            const repoLink = document.createElement('a');
            repoLink.href = repo.link;
            repoLink.target = '_blank';
            repoLink.innerHTML = `<div class="nome">${repo.nome}</div>`;
            newRepo.appendChild(repoLink);

            reposDiv.appendChild(newRepo);
        });

        // Chamar toggleDisplay após os repositórios serem carregados
        toggleDisplay();
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
}


// Carregar repositórios ao iniciar a página
window.onload = carregarRepositorios;




// Verificação de tipo para esconder funções

const usuarioLogadoRepositorios = JSON.parse(localStorage.getItem('usuarioLogado'));
let tipoVer;

if (usuarioLogadoRepositorios && usuarioLogadoRepositorios.tipo !== undefined) {
    tipoVer = usuarioLogadoRepositorios.tipo; // Atribui o valor dentro do 'if'
} else {
    console.error('Usuário inválido ou tipo não definido.');
}

    // tipo 3
    function toggleDisplay() {
        const addRepo = document.getElementById('addRepo');
        const deleteBtns = document.getElementsByClassName('deleteBtn'); 

        if (tipoVer == 3) {
            addRepo.style.display = 'none'; 

            Array.from(deleteBtns).forEach(btn => {
                btn.style.display = 'none'; 
            });
        }
    }

// Carregar os repositórios ao iniciar a página
window.onload = carregarRepositorios;