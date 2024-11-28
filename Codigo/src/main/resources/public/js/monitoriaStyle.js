const url = 'http://localhost:4567';
let currentOpenButton = null;
let monitorCache = {}; 

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
            const { nome, materia, email, foto, horario, sala } = monitorData;

            const button = criarBotaoMonitor(nome, materia, email, foto, horario, sala);
            container.appendChild(button);
        }
    } catch (error) {
        console.error('Erro ao mostrar monitores online:', error);
    }
}

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

async function getMonitorData(monitor) {
    if (monitorCache[monitor.id_monitor]) {
        return monitorCache[monitor.id_monitor]; 
    }

    try {
        const [nome, materia, email, foto, horario, sala] = await Promise.all([
            mostrarNome(monitor.id_monitor),
            fetchMateria(monitor.id_materia),
            mostrarEmail(monitor.id_monitor),
            mostrarFoto(monitor.id_monitor),
            mostrarHorarios(monitor.id_monitor),
            mostrarSala(monitor.id_monitor)
        ]);

        const monitorData = { nome, materia, email, foto, horario, sala };
        monitorCache[monitor.id_monitor] = monitorData;
        return monitorData;
    } catch (error) {
        console.error('Erro ao obter dados do monitor:', error);
        return { nome: 'Monitor não encontrado', materia: 'Não disponível', email: 'Não disponível', foto: '', horario: ['Horário não definido', ''], sala: 'Não definida' }; 
    }
}

function criarBotaoMonitor(nome, materia, email, foto, horario, sala) {
    if (!Array.isArray(horario) || horario.length < 2 || horario[0] == null || horario[1] == null) {
        horario = ["horário não definido", " "]; 
    }

    const local = sala !== undefined ? sala : "Descansando"; 

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
                <p class="local">Local: ${local}</p>
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

async function fetchMateria(id_materia) {
    try {
        const response = await fetch(`${url}/mostrarmateria/${id_materia}`);

        if (!response.ok) {
            throw new Error(`Erro na requisição da matéria: ${response.status}`);
        }

        const materia = await response.json();

        return materia.nome;
    } catch (error) {
        console.error('Erro ao buscar a matéria:', error);
        return 'Matéria não encontrada'; 
    }
}

async function mostrarEmail(id_monitor) {
    try {
        const response = await fetch(`${url}/mostraremail/${id_monitor}`);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        const email = await response.json();

        return email; 
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado';
    }
}

async function mostrarNome(id_monitor) {
    try {
        const response = await fetch(`${url}/mostrarnome/${id_monitor}`);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        const nome = await response.json();

        return nome;
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado'; 
    }
}

async function mostrarFoto(id_monitor) {
    try {
        const response = await fetch(`${url}/mostrarfoto/${id_monitor}`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar a imagem');
        }

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
        const response = await fetch(`${url}/mostrarsala/${id_monitor}`, { method: 'GET' });
        
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }
        
        const data = await response.json();
        
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
        const response = await fetch(`${url}/mostrarhorarios/${id_monitor}`);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        const data = await response.json();

        return data; 
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado';
    }
}

mostrarMonitoresOnline();
mostrarMonitoresOffline();
