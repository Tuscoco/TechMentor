const url = 'http://localhost:4567';
let currentOpenButton = null;

const cache = {
    monitores: null,
    fotos: new Map(),
    materias: new Map(),
    pontos: new Map(),
    emails: new Map(),
    atendimentos: new Map(),
    pontosMensais: new Map(),
};

async function mostrarMonitoresOnline() {
    try {
        let monitores;

        if (cache.monitores) {
            monitores = cache.monitores;
        } else {
            const response = await fetch(`${url}/mostrarusuarios/2`);
            if (!response.ok) {
                throw new Error(`Erro na requisição de monitores: ${response.status}`);
            }
            monitores = await response.json();
            cache.monitores = monitores;
        }

        const container = document.getElementById('monP');
        container.innerHTML = '';

        const dataPromises = monitores.map(async (monitor) => {
            const [materia, pontosTotais, email, foto, atendimentos] = await Promise.all([
                getCachedData(monitor.id, cache.materias, mostrarMateria),
                getCachedData(monitor.id, cache.pontos, mostrarPontos),
                getCachedData(monitor.id, cache.emails, mostrarEmail),
                getCachedData(monitor.id, cache.fotos, mostrarFoto),
                getCachedData(monitor.id, cache.atendimentos, mostrarAtendimentos),
            ]);

            return { monitor, materia, pontosTotais, email, foto, atendimentos };
        });

        const data = await Promise.all(dataPromises);

        for (const { monitor, materia, pontosTotais, email, foto, atendimentos } of data) {
            const button = criarBotaoMonitor(monitor, materia, pontosTotais, email, foto, atendimentos);
            container.appendChild(button);
        }
    } catch (error) {
        console.error('Erro ao mostrar monitores online:', error);
    }
}

function criarBotaoMonitor(monitor, materia, pontosTotais, email, foto, atendimentos) {
    const button = document.createElement('button');
    button.classList.add('menu-hamburger');

    button.innerHTML = `
        <div class="bar-close">
            <img src="${foto}" alt="foto">
            <p class="nome">${monitor.nome}</p>
            <div>
                <p class="ponto">Pontos: ${pontosTotais}</p>
            </div>
        </div>
        <div class="bar-open">
            <div class="info">
                <img src="${foto}" alt="foto">
                <div class="dados">
                    <p class="nome">Nome: ${monitor.nome}</p>
                    <p class="materia">Matéria: ${materia}</p>
                    <p class="email">Email: ${email}</p>
                    <p class="pontos">Pontos totais: ${pontosTotais}</p>
                    <div class="selectMes">
                        <p class="pontosMes">Pontos Mensais: </p>
                        <select class="mes">
                            <option default selected disabled class="disabled" style="display: none;">Selecione um mês</option>
                                <option value="1">Janeiro</option>
                                <option value="2">Fevereiro</option>
                                <option value="3">Março</option>
                                <option value="4">Abril</option>
                                <option value="5">Maio</option>
                                <option value="6">Junho</option>
                                <option value="7">Julho</option>
                                <option value="8">Agosto</option>
                                <option value="9">Setembro</option>
                                <option value="10">Outubro</option>
                                <option value="11">Novembro</option>
                                <option value="12">Dezembro</option>
                        </select>
                        <button class="send">Ok</button>
                    </div>
                </div>
            </div>
            <p>Atendimentos</p>
            <div>
                ${atendimentos.map((obj, index) => `
                    <div class="atendimento">
                        <p>Atendimento ${index + 1}:</p>
                        <p>Aluno: ${obj.id_aluno}</p>
                        <p>Data: ${obj.data}</p>
                        <p>Tema: ${obj.tema_duvida}</p>
                        <p>Descrição: ${obj.descricao}</p>
                        <p>A dúvida foi sanada?: ${obj.duvida_sanada}</p>
                    </div>
                `).join('')}
            </div>
        </div>
    `;

    configurarInteratividadeBotao(button, monitor);

    return button;
}

function configurarInteratividadeBotao(button, monitor) {
    const selectMes = button.querySelector('.mes');
    selectMes.addEventListener('click', (event) => event.stopPropagation());

    const send = button.querySelector('.send');
    send.addEventListener('click', async (event) => {
        event.stopPropagation();
        const mes = button.querySelector('.mes').value;
        if (!mes) {
            alert('Por favor, selecione um mês.');
            return;
        }

        try {
            const pontosMes = await getCachedData(
                `${monitor.id}-${mes}`,
                cache.pontosMensais,
                () => mostrarPontosMes(mes, monitor.id)
            );
            const pontosMesElement = button.querySelector('.pontosMes');
            pontosMesElement.textContent = `Pontos Mensais: ${pontosMes}`;
        } catch (error) {
            console.error('Erro ao buscar pontos por mês:', error);
        }
    });

    button.addEventListener('click', () => {
        if (currentOpenButton && currentOpenButton !== button) {
            currentOpenButton.classList.remove('open');
        }
        button.classList.toggle('open');
        currentOpenButton = button.classList.contains('open') ? button : null;
    });
}

async function getCachedData(key, cacheStore, fetchFunction) {
    if (cacheStore.has(key)) {
        return cacheStore.get(key);
    }
    const data = await fetchFunction(key);
    cacheStore.set(key, data);
    return data;
}

async function mostrarFoto(id_monitor) {
    const response = await fetch(`${url}/mostrarfoto/${id_monitor}`);
    if (!response.ok) throw new Error('Erro ao buscar a imagem');
    return await response.json();
}

async function mostrarMateria(id_monitor) {
    const response = await fetch(`${url}/buscarmateriamonitor/${id_monitor}`);
    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);
    const data = await response.json();
    return fetchMateria(data);
}

async function mostrarPontos(id_monitor) {
    const response = await fetch(`${url}/buscarpontos/${id_monitor}`);
    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);
    return (await response.json()).length;
}

async function fetchMateria(id_materia) {
    const response = await fetch(`${url}/mostrarmateria/${id_materia}`);
    if (!response.ok) throw new Error(`Erro na requisição da matéria: ${response.status}`);
    return (await response.json()).nome;
}

async function mostrarEmail(id_monitor) {
    const response = await fetch(`${url}/mostraremail/${id_monitor}`);
    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);
    return await response.json();
}

async function mostrarPontosMes(mes, id_monitor) {
    const response = await fetch(`${url}/buscarpontopelomes/${id_monitor}/${mes}`);
    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);
    return (await response.json()).length;
}

async function mostrarAtendimentos(id_monitor) {
    const response = await fetch(`${url}/buscaratendimentos/${id_monitor}`);
    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);
    return await response.json();
}

mostrarMonitoresOnline();
