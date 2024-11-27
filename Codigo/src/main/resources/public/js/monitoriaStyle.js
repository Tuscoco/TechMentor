const url = 'http://localhost:4567'; // Endereço do seu servidor
let currentOpenButton = null;

// Função para buscar monitores online e exibi-los na tela
async function mostrarMonitoresOnline() {
    try {
        // Faz a requisição GET para o servidor local
        const response = await fetch(`${url}/mostrarMonitoresOnline`);
        
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
            const materia = await fetchMateria(monitor.id_materia); // Certifique-se de que id_materia existe
            const email = await mostrarEmail(monitor.id_monitor);
            const nome = await mostrarNome(monitor.id_monitor);
            const foto = await mostrarFoto(monitor.id_monitor);
            const sala = await mostrarSala(monitor.id_monitor);
            // Cria um botão para cada monitor
            const button = document.createElement('button');
            button.classList.add('menu-hamburger');

            // Estrutura do conteúdo do botão (fechado e aberto)
            button.innerHTML = `
                <div class="bar-close">
                    <img src="${foto}" alt="foto">
                    <p class="nome">${nome}</p>
                    <div>
                    <p class="materia">${materia}</p>
                    </div>
                    <p class="horario">13:00 - 16:00</p>
                </div>
                <div class="bar-open">
                    <img src="${foto}" alt="foto">
                    <div class="dados">
                        <p class="nome">Nome: ${nome}</p>
                        <p class="materia">Matéria: ${materia}</p>
                        <p class="horario">Horário: 13:00 - 16:00</p>
                        <p class="local">Local: ${sala}</p>
                        <p class="contato">Contato: ${email}</p>
                    </div>
                </div>
            `;

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

// Função para buscar monitores offline (mantém o mesmo comportamento)
async function mostrarMonitoresOffline() {
    try {
        // Faz a requisição GET para o servidor local
        const response = await fetch('/mostrarMonitoresOffline');
        
        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição de monitores: ${response.status}`);
        }

        const monitores = await response.json(); // Converte a resposta em JSON

        // Seleciona o contêiner onde os botões serão inseridos
        const container = document.getElementById('monA');
        container.innerHTML = ''; // Limpa o conteúdo anterior

        // Itera sobre os monitores retornados
        for (const monitor of monitores) {

            // Obtém a matéria do monitor
            const materia = await fetchMateria(monitor.id_materia); // Certifique-se de que id_materia existe
            const email = await mostrarEmail(monitor.id_monitor);
            const nome = await mostrarNome(monitor.id_monitor);
            const foto = await mostrarFoto(monitor.id_monitor);
            const horario = await mostrarHorarios(monitor.id_monitor);
            console.log(horario);
            if(horario[0] == null || horario[0] == null){
                horario[0] = "horario não definido"
                horario[1] = " "
            }    
            
            // Cria um botão para cada monitor
            const button = document.createElement('button');
            button.classList.add('menu-hamburger');

            // Estrutura do conteúdo do botão (fechado e aberto)
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
                        <p class="local">Local: Denscansando</p>
                        <p class="contato">Contato: ${email}</p>
                    </div>
                </div>
            `;

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
        console.error('Erro ao mostrar monitores offline:', error);
    }
}

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

// Chama as funções para exibir os monitores online e offline
mostrarMonitoresOnline();
mostrarMonitoresOffline();
