// Variável global para armazenar o botão atualmente aberto
let currentOpenButton = null;

// Função para buscar monitores online e exibi-los na tela
async function mostrarMonitoresOnline() {
    try {
        // Faz a requisição GET para o servidor local
        const response = await fetch('http://localhost:4567/mostrarMonitoresOnline');
        
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

            // Cria um botão para cada monitor
            const button = document.createElement('button');
            button.classList.add('menu-hamburger');

            // Estrutura do conteúdo do botão (fechado e aberto)
            button.innerHTML = `
                <div class="bar-close">
                    <img src="../assets/img/3535250.png" alt="">
                    <p class="nome">${nome}</p>
                    <div>
                    <p class="materia">${materia}</p>
                    </div>
                    <p class="horario">13:00 - 16:00</p>
                </div>
                <div class="bar-open">
                    <img src="../assets/img/3535250.png" alt="">
                    <div class="dados">
                        <p class="nome">Nome: ${nome}</p>
                        <p class="materia">Matéria: ${materia}</p>
                        <p class="horario">Horário: 13:00 - 16:00</p>
                        <p class="local">Local: ${monitor.sala}</p>
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
        const response = await fetch('http://localhost:4567/mostrarMonitoresOffline');
        
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
            // Cria um botão para cada monitor
            const button = document.createElement('button');
            button.classList.add('menu-hamburger');

            // Estrutura do conteúdo do botão (fechado e aberto)
            button.innerHTML = `
                <div class="bar-close">
                    <img src="../assets/img/3535250.png" alt="">
                    <p class="nome">${nome}</p>
                    <div>
                    <p class="materia">${materia}</p>
                    </div>
                    <p class="horario">13:00 - 16:00</p>
                </div>
                <div class="bar-open">
                    <img src="../assets/img/3535250.png" alt="">
                    <div class="dados">
                        <p class="nome">Nome: ${nome}</p>
                        <p class="materia">Matéria: ${materia}</p>
                        <p class="horario">Horário: 13:00 - 16:00</p>
                        <p class="local">Local: ${monitor.sala}</p>
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
        const response = await fetch(`http://localhost:4567/mostrarmateria/${id_materia}`);

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
        const response = await fetch(`http://localhost:4567/mostraremail/${id_monitor}`);

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
        const response = await fetch(`http://localhost:4567/mostrarnome/${id_monitor}`);

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

// Chama as funções para exibir os monitores online e offline
mostrarMonitoresOnline();
mostrarMonitoresOffline();
