// Seleciona o botão de enviar
const enviarButton = document.getElementById('enviar');

// Adiciona um evento de clique ao botão
enviarButton.addEventListener('click', function () {
    // Coleta os valores dos campos do formulário
    const disciplina = document.getElementById('elementDisciplina').value;
    const data = document.getElementById('elementData').value;
    const horario = document.getElementById('elementHorario').value;
    const matricula = document.getElementById('elementMatricula').value;
    const duvida = document.getElementById('elementDuv').value;
    const duvidaDesc = document.getElementById('elementDuvDesc').value;
    const sanada = document.querySelector('input[name="duvida"]:checked')?.value; // Obtém o valor do rádio selecionado
    const observacoes = document.getElementById('elementObs').value;

    // Verifica se todos os campos estão preenchidos
    if (!disciplina || !data || !horario || !matricula || !duvida || !duvidaDesc || !sanada) {
        alert("Por favor, preencha todos os campos.");
        return; // Interrompe a execução se algum campo estiver vazio
    }

    // Cria um objeto com os dados a serem enviados
    const dados = {
        disciplina,
        data,
        horario,
        matricula,
        duvida,
        duvidaDesc,
        sanada,
        observacoes
    };

    // Envia os dados para o JSON Server
    fetch('http://localhost:3000/atendimentos', { // Substitua pela URL do seu JSON Server
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        alert('Dados enviados com sucesso!');
        console.log('Success:', data);
    })
    .catch((error) => {
        alert('Erro ao enviar os dados: ' + error.message);
        console.error('Error:', error);
    });
});
