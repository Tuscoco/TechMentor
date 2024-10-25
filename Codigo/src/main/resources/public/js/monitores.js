const usLogMon = JSON.parse(sessionStorage.getItem('usuarioLogado'));


// Função para buscar e exibir os nomes dos usuários do tipo 3
async function fetchUsuarios() {
    try {
        const tipoLogado = usLogMon.tipo;

        // Fazendo a requisição GET para a API
        const response = await fetch('http://localhost:4567/mostrarusuarios/3');

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const usuarios = await response.json();

        // Selecionando a div 'alunos'
        const alunosDiv = document.getElementById('alunos');

        // Limpando o conteúdo da div
        alunosDiv.innerHTML = '';

        // Iterando sobre cada usuário e criando a estrutura desejada
        usuarios.forEach(usuario => {
            // Criando a div 'aluno'
            const alunoDiv = document.createElement('div');
            alunoDiv.classList.add('aluno');

            // Seção de informações
            const infoSection = document.createElement('section');
            infoSection.classList.add('info');

            // Dividindo o nome completo em partes (usando espaço como separador)
            const nomeCompleto = usuario.nome.split(' ');

            // Pegando apenas o primeiro e segundo nome (se houver)
            const primeiroNome = nomeCompleto[0];
            const segundoNome = nomeCompleto[1] ? nomeCompleto[1] : '';

            // Exibindo o primeiro e segundo nome juntos
            const nomeParagrafo = document.createElement('p');
            nomeParagrafo.classList.add('nome');
            nomeParagrafo.textContent = `${primeiroNome} ${segundoNome}`;
            infoSection.appendChild(nomeParagrafo);

            // Seção de disciplinas (select)
            const dadosSection = document.createElement('section');
            dadosSection.classList.add('dados');
            const selectMateria = document.createElement('select');
            selectMateria.classList.add('materia');

            // Opção padrão
            const optionDefault = document.createElement('option');
            optionDefault.value = '';
            optionDefault.textContent = 'selecione uma Matéria';
            optionDefault.disabled = true;
            optionDefault.selected = true;
            optionDefault.classList.add('default');
            selectMateria.appendChild(optionDefault);

            // Opções de disciplinas
            const disciplinas = [
                'Algoritmos e Estruturas de dados I', 'Algoritmos e Estruturas de dados II',
                'Algoritmos e Estruturas de dados III', 'Arquitetura de Computadores I',
                'Arquitetura de Computadores II', 'Arquitetura de Computadores III',
                'Banco de Dados', 'Cálculo I', 'Cálculo II', 'Compiladores',
                'Computação Gráfica', 'Desenvolvimento de Interfaces Web',
                'Eletrônica para Computação', 'Engenharia de Software I',
                'Engenharia de Software II', 'Física Mecânica',
                'Fundamentos Teóricos da Computação', 'Geometria Analítica e Álgebra Linear',
                'Inteligência Artificial', 'Internet das Coisas I', 'Internet das Coisas II',
                'Introdução à Computação', 'Introdução aos Sistemas Inteligentes',
                'Laboratório de Desenvolvimento de Dispositivos Móveis',
                'Laboratório de Iniciação à Programação',
                'Laboratório de Introdução à Engenharia de Computação',
                'Linguagens de Programação', 'Modelagem e Avaliação de Desempenho',
                'Projeto e Análise de Algoritmos', 'Redes de Computadores I',
                'Redes de Computadores II', 'Sistemas Embarcados', 'Sistemas Operacionais',
                'Teoria dos Grafos e Computabilidade', 'Trabalho Interdisciplinar I: Frontend',
                'Trabalho Interdisciplinar II: Backend', 'Otimização de Sistemas',
                'Segurança e Auditoria'
            ];

            // Adicionando as opções ao select
            disciplinas.forEach((disciplina, index) => {
                const option = document.createElement('option');
                option.value = index + 1; // index + 1 para coincidir com os valores sugeridos
                option.textContent = disciplina;
                selectMateria.appendChild(option);
            });

            dadosSection.appendChild(selectMateria);

            // Seção de botão 'Adicionar'
            const addSection = document.createElement('section');
            addSection.classList.add('addSection');
            const addButton = document.createElement('button');
            addButton.classList.add('addBtn');

            const btnDefault = document.createElement('p');
            btnDefault.classList.add('btnDefault');
            btnDefault.textContent = '+';

            const btnHover = document.createElement('p');
            btnHover.classList.add('btnHover');
            btnHover.textContent = 'Adicionar';

            const idUsuario = usuario.id;

            addButton.appendChild(btnDefault);
            addButton.appendChild(btnHover);
            addSection.appendChild(addButton);

            // Adicionando o event listener ao botão
            addButton.addEventListener('click', async () => {

                if (tipoLogado == 0) {
                    try {
                        // Capturando o valor da matéria selecionada
                        const materiaSelecionada = selectMateria.value;
                        if (materiaSelecionada === '') {
                            alert('Por favor, selecione uma matéria antes de continuar.');
                            return; // Interrompe o fluxo caso nenhuma matéria tenha sido selecionada
                        }

                        // Criando o objeto com os dados para a requisição
                        const alteraTipo = {
                            idAlterador: tipoLogado, // O ID a ser enviado
                            idAlvo: idUsuario,
                            novoTipo: 2,
                            idMateria: materiaSelecionada
                        };

                        console.log(alteraTipo);

                        // Fazendo a requisição POST
                        const response = await fetch('http://localhost:4567/alterartipousuario', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(alteraTipo) // Convertendo o objeto em JSON
                        });

                        // Verificando se a resposta foi bem-sucedida
                        if (!response.ok) {
                            throw new Error(`Erro na requisição: ${response.status}`);
                        }

                        // Processando a resposta se necessário
                        const data = await response.json();
                        console.log('Resposta do servidor:', data);

                        window.location.reload();


                    } catch (error) {
                        console.error('Erro ao alterar tipo de usuário:', error);
                    }
                } else {
                    console.log('sem permissão');
                }
            });

            // Adicionando as seções à div 'aluno'
            alunoDiv.appendChild(infoSection);
            alunoDiv.appendChild(dadosSection);
            alunoDiv.appendChild(addSection);

            // Adicionando a div 'aluno' à div 'alunos'
            alunosDiv.appendChild(alunoDiv);
        });

    } catch (error) {
        console.error('Erro ao buscar os usuários:', error);
    }
}

async function fetchMonitores() {
    try {
        const tipoLogado = usLogMon.tipo;

        // Fazendo a requisição GET para a API
        const response = await fetch('http://localhost:4567/mostrarusuarios/2');

        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const usuarios = await response.json();

        // Selecionando a div 'alunos'
        const alunosDiv = document.getElementById('monitores');

        // Limpando o conteúdo da div
        alunosDiv.innerHTML = '';

        // Iterando sobre cada usuário e criando a estrutura desejada
        for (const usuario of usuarios) {
            // Criando a div 'aluno'
            const alunoDiv = document.createElement('div');
            alunoDiv.classList.add('monitor');

            // Seção de informações
            const infoSection = document.createElement('section');
            infoSection.classList.add('info');

            // Dividindo o nome completo em partes (usando espaço como separador)
            const nomeCompleto = usuario.nome.split(' ');
            const primeiroNome = nomeCompleto[0];
            const segundoNome = nomeCompleto[1] ? nomeCompleto[1] : '';

            // Exibindo o primeiro e segundo nome juntos
            const nomeParagrafo = document.createElement('p');
            nomeParagrafo.classList.add('nome');
            nomeParagrafo.textContent = `${primeiroNome} ${segundoNome}`;
            infoSection.appendChild(nomeParagrafo);

            // Seção de dados (matéria)
            const dadosSection = document.createElement('section');
            dadosSection.classList.add('dados');
            const materia = document.createElement('p');
            materia.classList.add('materia');
            materia.textContent = 'Carregando...'; // Texto temporário enquanto a matéria é carregada
            dadosSection.appendChild(materia);

            // Buscando o nome da matéria e atualizando o texto
            const idMateria = await buscarMateria(usuario.id);
            const nomeMateria = await fetchMateria(idMateria);
            materia.textContent = nomeMateria; // Atualiza o texto com o nome da matéria

            // Seção de botão 'Adicionar'
            const rmvSection = document.createElement('section');
            rmvSection.classList.add('rmvSection');
            const rmvButton = document.createElement('button');
            rmvButton.classList.add('rmvBtn');

            const btnDefault = document.createElement('p');
            btnDefault.classList.add('btnDefault');
            btnDefault.textContent = '-';

            const btnHover = document.createElement('p');
            btnHover.classList.add('btnHover');
            btnHover.textContent = 'Remover';

            const idUsuario = usuario.id;

            rmvButton.appendChild(btnDefault);
            rmvButton.appendChild(btnHover);
            rmvSection.appendChild(rmvButton);

            // Event listener para remover monitor
            rmvButton.addEventListener('click', async () => {
                if (tipoLogado == 0) {
                    try {
                        // Criando o objeto com os dados para a requisição
                        const alteraTipo = {
                            idAlterador: tipoLogado,
                            idAlvo: idUsuario,
                            novoTipo: 3,
                            idMateria: 0
                        };

                        // Fazendo a requisição POST
                        const response = await fetch('http://localhost:4567/alterartipousuario', {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            body: JSON.stringify(alteraTipo)
                        });

                        if (!response.ok) {
                            throw new Error(`Erro na requisição: ${response.status}`);
                        }

                        // Recarregar a página após sucesso
                        window.location.reload();
                    } catch (error) {
                        console.error('Erro ao alterar tipo de usuário:', error);
                    }
                } else {
                    console.log('Sem permissão');
                }
            });

            // Adicionando as seções à div 'aluno'
            alunoDiv.appendChild(infoSection);
            alunoDiv.appendChild(dadosSection);
            alunoDiv.appendChild(rmvSection);

            // Adicionando a div 'aluno' à div 'alunos'
            alunosDiv.appendChild(alunoDiv);
        }

    } catch (error) {
        console.error('Erro ao buscar os usuários:', error);
    }
}


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

async function buscarMateria(id_monitor) {
    const url = `http://localhost:4567/buscarmateriamonitor/${id_monitor}`;

    try {
        // Fazendo a requisição GET
        const response = await fetch(url);
        
        // Verificando se a resposta foi bem-sucedida
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status}`);
        }

        // Convertendo a resposta para JSON
        const data = await response.json();

        // Exibindo o resultado no console (ou retornando os dados)
        console.log("Matéria vinculada:", data);
        return data;
    } catch (error) {
        console.error("Erro ao buscar a matéria:", error);
    }
}

fetchMonitores();
fetchUsuarios();