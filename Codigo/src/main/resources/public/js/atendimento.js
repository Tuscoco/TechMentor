const usLogAtendimento = JSON.parse(sessionStorage.getItem('usuarioLogado'));

// Seleciona o botão de enviar
const enviarButton = document.getElementById('enviar');

// Adiciona um evento de clique ao botão
enviarButton.addEventListener('click', function () {
    salvarAtendimento();
});

async function salvarAtendimento() {
    // Obter os elementos do HTML (inputs, textareas, etc.)
    const usLogAtendimento = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    
    if (!usLogAtendimento || !usLogAtendimento.id) {
        console.error("Usuário não está logado ou ID não encontrado.");
        return;
    }

    
    // const descricao =
    
    const idMonitor = usLogAtendimento.id; // ID do monitor logado
    const idAluno = document.getElementById('elementMatricula').value;
    const idMateria = document.getElementById('elementDisciplina').value; 
    const data = document.getElementById('elementData').value;
    const temaDuvida = document.getElementById('elementDuv').value;
    const descricao =  document.getElementById('elementDuvDesc').value;
    const duvida_sanada_value = document.querySelector('input[name="duvida"]:checked')?.value;
    let duvidaSanada;

    // Verifica se o valor é 'sim' ou 'não'
    if (duvida_sanada_value === 'sim') {
        duvidaSanada = true; // Atribui true se o valor for 'sim'
    } else if (duvida_sanada_value === 'não') {
        duvidaSanada = false; // Atribui false se o valor for 'não'
    } else {
        duvidaSanada = null; // Opcional: define como null se não for 'sim' ou 'não'
    }

    if (!idMonitor || !idAluno || !idMateria || !data || !temaDuvida || !descricao || duvidaSanada === null) {
        alert("Por favor, preencha todos os campos.");
        return; // Interrompe a execução se algum campo estiver vazio
    }

    // Criar o objeto a ser enviado no corpo da requisição
    const atendimento = {
        id_monitor: parseInt(idMonitor),
        id_aluno: idAluno,
        id_materia: parseInt(idMateria) ,
        data: data,
        tema_duvida: temaDuvida,
        descricao: descricao,
        duvida_sanada: duvidaSanada
    };

    console.log('Objeto Atendimento:', atendimento);

    try {
        const response = await fetch('http://localhost:4567/salvaratendimento', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(atendimento)
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Atendimento salvo com sucesso:', result);
        } else {
            const errorResult = await response.json();
            console.error('Erro ao salvar atendimento:', response.status, response.statusText, errorResult);
        }
    } catch (error) {
        console.error('Erro de rede:', error.message);
    }
}

