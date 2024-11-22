const url = 'http://localhost:4567'; // Endereço do seu servidor

// Função para definir o estado inicial da página
function setInitialState() {
    showLogin(); // Inicialmente mostra a seção de login
}

// Adiciona um evento de carregamento da página
window.addEventListener('load', setInitialState);

// Função para mostrar a seção de login e ocultar a de cadastro
function showLogin() {
    document.getElementById('entrar').style.display = 'flex';
    document.getElementById('cadastrar').style.display = 'none';
    
    // Adiciona a classe 'active' ao botão 'Entrar' e remove do 'Inscrever-se'
    document.querySelector('#switch button:nth-child(1)').classList.add('active');
    document.querySelector('#switch button:nth-child(2)').classList.remove('active');
}

// Função para mostrar a seção de cadastro e ocultar a de login
function showSignup() {
    document.getElementById('entrar').style.display = 'none';
    document.getElementById('cadastrar').style.display = 'flex';
    
    // Adiciona a classe 'active' ao botão 'Inscrever-se' e remove do 'Entrar'
    document.querySelector('#switch button:nth-child(1)').classList.remove('active');
    document.querySelector('#switch button:nth-child(2)').classList.add('active');
}

// Função para adicionar ou remover o novo campo e alterar a margem do botão
function toggleNewField() {
    var checkbox = document.getElementById('aceitar');
    var additionalFields = document.getElementById('additionalFields');
    var cadastrarBtn = document.getElementById('cadastrarBtn');

    // Verifica se a caixa de seleção está marcada
    if (checkbox.checked) {
        // Cria um novo campo de entrada
        var newInput = document.createElement('input');
        newInput.type = 'Number';
        newInput.placeholder = 'Código de coordenador';

        // Adiciona o novo campo ao contêiner
        additionalFields.appendChild(newInput);
        
        // Altera a margem do botão 'Cadastrar'
        cadastrarBtn.style.marginTop = '10px'; // Ajuste a margem conforme necessário
    } else {
        // Remove o novo campo de entrada se a caixa de seleção for desmarcada
        additionalFields.innerHTML = '';
        
        // Restaura a margem do botão 'Cadastrar'
        cadastrarBtn.style.marginTop = '30px'; // Ajuste a margem original conforme necessário
    }
}
    
// Captura o botão de cadastro
const cadastrarBtn = document.getElementById('cadastrarBtn');

    // Função para enviar os dados de cadastro para o servidor
cadastrarBtn.addEventListener('click', async (e) => {
    e.preventDefault(); // Evita a recarga da página ao clicar no botão

    // Captura os valores dos campos de cadastro
    const nome = document.querySelector('#cadastrar input[placeholder="Nome"]').value;
    const id = document.querySelector('#cadastrar input[placeholder="ID"]').value;
    const email = document.querySelector('#cadastrar input[placeholder="Email"]').value;
    const senha = document.querySelector('#cadastrar input[placeholder="Senha"]').value;
    const confirmarSenha = document.querySelector('#cadastrar input[placeholder="Confirmar Senha"]').value;

    // Validação básica para verificar se as senhas coincidem
    if (senha !== confirmarSenha) {
        alert('As senhas não correspondem!');
        return;
    }

    // Criação do objeto que será enviado para o banco de dados
    const novoUsuario = {
        id: Number(id),
        nome,
        email,
        senha,
        tipo: "3"
    };

    try {
        // Envio da requisição POST para o JSON Server
        const resposta = await fetch(`${url}/registrarpessoa`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(novoUsuario)
        });

        if (resposta.ok) {
            alert('Usuário cadastrado com sucesso!');
            localStorage.setItem('usuarioLogado', JSON.stringify(novoUsuario));
            window.location.href = '../html/home.html';
        } else {
            alert('Erro ao cadastrar o usuário.');
        }
    } catch (erro) {
        console.error('Erro ao conectar com o servidor:', erro);
        alert('Erro de conexão com o servidor.');
    }
});

// LOGIN
const loginBtn = document.querySelector('#entrar button');

loginBtn.addEventListener('click', () => {
    const id = document.querySelector('#entrar input[placeholder="ID"]').value.trim();
    const senha = document.querySelector('#entrar input[placeholder="Senha"]').value.trim();

    // Verifica se o ID ou Senha estão em branco
    if (!id || !senha) {
        alert('Por favor, preencha todos os campos.');
        return; // Interrompe a execução se algum campo estiver vazio
    }

    loginPessoa(id, senha);
});

async function loginPessoa(id, senha, tipo, nome) {
    const pessoa = {
        id: id,
        senha: senha
    };

    const userLogado = {
        nome: nome,
        id: id,
        senha: senha,
        tipo: tipo
    };

    try {
        const response = await fetch(`${url}/loginpessoa`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pessoa)
        });

        if (response.ok) {
            const result = await response.json();

            // Verifica a mensagem retornada
            if (result === "Login concluido!") {

                const id = userLogado.id;
        
                // Aguarda a resposta das funções assíncronas
                userLogado.tipo = await getTipoUsuario(id);
                userLogado.nome = await getNomeUsuario(id);

                localStorage.setItem('usuarioLogado', JSON.stringify(userLogado))
                console.log(userLogado)
                window.location.href = '../html/home.html';
            } else {
                console.log("Falha no login:", result);
                alert("Falha no login: " + result); // Alerta se ID ou senha estiverem errados
            }
        } else {
            console.error('Erro na requisição:', response.status);
            alert('Erro na requisição: ' + response.status);
        }
    } catch (error) {
        console.error('Erro ao realizar o login:', error);
        alert('Erro ao realizar o login');
    }
}

async function getTipoUsuario(id) {
    try {
        const response = await fetch(`${url}/tipousuario/${id}`, { 
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
        const response = await fetch(`${url}/mostrarnome/${id}`, { 
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