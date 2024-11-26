const url = 'http://localhost:4567'; // Endereço do seu servidor
let currentOpenButton = null;

// Função para buscar monitores online e exibi-los na tela
async function mostrarMonitoresOnline() {
    try {
        // Faz a requisição GET para o servidor local
        const response = await fetch(`${url}/mostrarusuarios/2`);
        
        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição de monitores: ${response.status}`);
        }

        const monitores = await response.json(); // Converte a resposta em JSON

        // Seleciona o contêiner onde os botões serão inseridos
        const container = document.getElementById('monP');
        container.innerHTML = ''; // Limpa o conteúdo anterior

        // Itera sobre os monitores retornados
        for (const monitor of monitores) {
            
            // Obtém a matéria do monitor
            const nome = monitor.nome;
            const materia = await mostrarMateria(monitor.id);
            const pontosTotais = await mostrarPontos(monitor.id);
            const email = await mostrarEmail(monitor.id);
            const foto = await mostrarFoto(monitor.id);
            let pontosMes = null;

            const button = document.createElement('button');
            button.classList.add('menu-hamburger');

            // Estrutura do conteúdo do botão (fechado e aberto)
            button.innerHTML = `
                <div class="bar-close">
                    <img src="${foto}" alt="foto">
                    <p class="nome">${nome}</p>
                    <div>
                    <p class="ponto">Pontos: ${pontosTotais}</p>
                    </div>
                </div>
                <div class="bar-open">
                    <img src="${foto}" alt="foto">
                    <div class="dados">
                        <p class="nome">Nome: ${nome}</p>
                        <p class="materia">Matéria: ${materia}</p>
                        <p class="email">Email: ${email}</p>
                        <p class="pontos">Pontos totais: ${pontosTotais}</p>
                        <div class="selectMes">
                            <p class="pontosMes">Pontos Mensais: ${pontosMes}</p>
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
            `;

            const selectMes = button.querySelector('.mes');
            selectMes.addEventListener('click', (event) => { // Tornar a função async
                event.stopPropagation();
            });
            
            const send = button.querySelector('.send');
            send.addEventListener('click', async (event) => {
                event.stopPropagation();
                
                // Obtém o valor do mês selecionado
                const mes = button.querySelector('.mes').value;
            
                // Verifica se um mês foi selecionado
                if (!mes) {
                    alert('Por favor, selecione um mês.');
                    return;
                }
            
                try {
                    // Faz a requisição para buscar os pontos do mês
                    const pontosMes = await mostrarPontosMes(mes, monitor.id);
            
                    // Atualiza o elemento com os pontos mensais
                    const pontosMesElement = button.querySelector('.pontosMes');
                    pontosMesElement.textContent = `Pontos Mensais: ${pontosMes}`;
                } catch (error) {
                    console.error('Erro ao buscar pontos por mês:', error);
                }
            });
            

            // Adiciona um evento de clique ao botão
            button.addEventListener('click', () => {
                // Fecha o botão atualmente aberto, se houver
                if (currentOpenButton && currentOpenButton !== button) {
                    currentOpenButton.classList.remove('open');
                }

                // Alterna a classe 'open' no botão atual
                button.classList.toggle('open');

                // Atualiza a referência do botão atualmente aberto
                currentOpenButton = button.classList.contains('open') ? button : null;
            });

            // Adiciona o botão ao contêiner
            container.appendChild(button);
        }
    } catch (error) {
        console.error('Erro ao mostrar monitores online:', error);
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

async function mostrarMateria(id_monitor) {

    try {
        // Fazendo a requisição GET
        const response = await fetch(`${url}/buscarmateriamonitor/${id_monitor}`);
        
        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const data = await response.json();

        // Exibindo o resultado no console (ou retornando os dados)
        const materia = fetchMateria(data);
        return materia;
    } catch (error) {
        console.error("Erro ao buscar a matéria:", error);
    }
}

async function mostrarPontos(id_monitor) {
    
    try {
        // Fazendo a requisição GET
        const response = await fetch(`${url}/buscarpontos/${id_monitor}`);
        
        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const data = await response.json();

        // Exibindo o resultado no console (ou retornando os dados)
        return data.length;
    } catch (error) {
        console.error("Erro ao buscar a pontos:", error);
    }
}

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

async function mostrarPontosMes(mes, id_monitor) {
    try {
        // Fazendo a requisição GET para a API
        const response = await fetch(`${url}/buscarpontopelomes/${id_monitor}/${mes}`);

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const data = await response.json();

        return data.length; 
        
    } catch (error) {
        console.error('Erro ao buscar o monitor:', error);
        return 'Monitor não encontrado'; // Valor padrão em caso de erro
    }
}

// Chama as funções para exibir os monitores online e offline
mostrarMonitoresOnline();
