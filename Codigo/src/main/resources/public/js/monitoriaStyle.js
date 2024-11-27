const url = 'http://localhost:4567'; // Endereço do seu servidor
let currentOpenButton = null;
let monitorCache = {}; // Objeto para armazenar os dados no cache

// Função para buscar monitores online e exibi-los na tela
async function mostrarMonitoresOnline() {
    try {
        const response = await fetch(`${url}/mostrarMonitoresOnline`);
        if (!response.ok) {
            throw new Error(`Erro na requisição de monitores: ${response.status}`);
        }
        const monitores = await response.json();

        const container = document.getElementById('monP');
        container.innerHTML = '';

        for (const monitor of monitores) {
            const monitorData = await getMonitorData(monitor);
            const { nome, materia, email, foto, horario } = monitorData;

            const button = criarBotaoMonitor(nome, materia, email, foto, horario);
            container.appendChild(button);
        }
    } catch (error) {
        console.error('Erro ao mostrar monitores online:', error);
    }
}

// Função para buscar monitores offline
async function mostrarMonitoresOffline() {
    try {
        const response = await fetch(`${url}/mostrarMonitoresOffline`);
        if (!response.ok) {
            throw new Error(`Erro na requisição de monitores: ${response.status}`);
        }
        const monitores = await response.json();

        const container = document.getElementById('monA');
        container.innerHTML = '';

        for (const monitor of monitores) {
            const monitorData = await getMonitorData(monitor);
            const { nome, materia, email, foto, horario } = monitorData;

            const button = criarBotaoMonitor(nome, materia, email, foto, horario);
            container.appendChild(button);
        }
    } catch (error) {
        console.error('Erro ao mostrar monitores offline:', error);
    }
}

// Função para obter dados de um monitor (com cache)
async function getMonitorData(monitor) {
    if (monitorCache[monitor.id_monitor]) {
        return monitorCache[monitor.id_monitor]; // Retorna do cache se disponível
    }

    try {
        // Buscando os dados em paralelo
        const [nome, materia, email, foto, horario] = await Promise.all([
            mostrarNome(monitor.id_monitor),
            fetchMateria(monitor.id_materia),
            mostrarEmail(monitor.id_monitor),
            mostrarFoto(monitor.id_monitor),
            mostrarHorarios(monitor.id_monitor)
        ]);

        const monitorData = { nome, materia, email, foto, horario };
        monitorCache[monitor.id_monitor] = monitorData; // Salva no cache
        return monitorData;
    } catch (error) {
        console.error('Erro ao obter dados do monitor:', error);
        return { nome: 'Monitor não encontrado', materia: 'Não disponível', email: 'Não disponível', foto: '', horario: ['Horário não definido', ''] }; // Valor padrão em caso de erro
    }
}


// Função para criar o botão de monitor
function criarBotaoMonitor(nome, materia, email, foto, horario) {
    // Verifica se "horario" é válido e possui pelo menos 2 elementos
    if (!Array.isArray(horario) || horario.length < 2 || horario[0] == null || horario[1] == null) {
        horario = ["horário não definido", " "]; // Define valores padrão
    }

    const button = document.createElement('button');
    button.classList.add('menu-hamburger');

    button.innerHTML = `
        <div class="bar-close">
            <img src="${foto}" alt="foto">
            <p class="nome">${nome}</p>
            <div>
                <p class="materia">${materia}</p>
            </div>
            <p class="horario">${horario[0]} - ${horario[1]}</p>
        </div>
        <div class="bar-open">
            <img src="${foto}" alt="foto">
            <div class="dados">
                <p class="nome">Nome: ${nome}</p>
                <p class="materia">Matéria: ${materia}</p>
                <p class="horario">Horário: ${horario[0]} - ${horario[1]}</p>
                <p class="local">Local: Descansando</p>
                <p class="contato">Contato: ${email}</p>
            </div>
        </div>
    `;

    button.addEventListener('click', () => {
        if (currentOpenButton && currentOpenButton !== button) {
            currentOpenButton.classList.remove('open');
        }
        button.classList.toggle('open');
        currentOpenButton = button.classList.contains('open') ? button : null;
    });

    return button;
}

// Funções auxiliares para buscar informações específicas (já no seu código, sem alterações)
// Função para buscar uma matéria pelo ID
async function fetchMateria(id_materia) {
    try {
        const response = await fetch(`${url}/mostrarmateria/${id_materia}`);

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição da matéria: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const materia = await response.json();

        // Retornando o nome da matéria
        return materia.nome; // Certifique-se de que o JSON retornado tem o campo "nome"
    } catch (error) {
        console.error('Erro ao buscar a matéria:', error);
        return 'Matéria não encontrada'; // Valor padrão em caso de erro
    }
}

async function mostrarEmail(id_monitor) {
    try {
        // Fazendo a requisição GET para a API
        const response = await fetch(`${url}/mostraremail/${id_monitor}`);

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const email = await response.json();
        // Retornando o email
        return email; // Certifique-se de que o JSON retornado tem o campo "email"
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado'; // Valor padrão em caso de erro
    }
}

async function mostrarNome(id_monitor) {
    try {
        // Fazendo a requisição GET para a API
        const response = await fetch(`${url}/mostrarnome/${id_monitor}`);

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const nome = await response.json();
        // Retornando o nome
        return nome; // Certifique-se de que o JSON retornado tem o campo "email"
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado'; // Valor padrão em caso de erro
    }
}

async function mostrarFoto(id_monitor) {
    try {

        // Faz o fetch para obter a URL da imagem
        const response = await fetch(`${url}/mostrarfoto/${id_monitor}`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar a imagem');
        }

        // Converte a resposta para JSON
        const foto = await response.json(); 

        if (foto) {
            return foto;
        } else {
            console.error('Elementos com a classe "profileImage" não encontrados.');
        }
    } catch (error) {
        console.error('Erro ao carregar a imagem de perfil:', error);
    }
}

async function mostrarSala(id_monitor) {    
    try {
        // Fazendo a requisição GET
        const response = await fetch(`${url}/mostrarsala/${id_monitor}`, { method: 'GET' });
        
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }
        
        // Obtendo o texto da resposta
        const data = await response.json();
        
        
        // Preenchendo o campo de texto com o valor retornado
        
        if (data) {
            return data;
        } else {
            console.error("Elemento com ID 'sala' não encontrado.");
        }
    } catch (error) {
        console.error("Erro ao buscar a sala:", error);
    }
}

async function mostrarHorarios(id_monitor) {
    try {
        // Fazendo a requisição GET para a API
        const response = await fetch(`${url}/mostrarhorarios/${id_monitor}`);

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const data = await response.json();
        // Retornando o data
        return data; // Certifique-se de que o JSON retornado tem o campo "email"
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado'; // Valor padrão em caso de erro
    }
}

// Chama as funções para exibir os monitores
mostrarMonitoresOnline();
mostrarMonitoresOffline();
