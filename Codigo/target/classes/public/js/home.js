const button = document.getElementById('btn');
const salaInput = document.getElementById('sala');
const mudarSwitch = document.getElementById('switch');
const sendButton = document.getElementById('sendBtn');


// Alterna a classe 'active' ao clicar no botão
button.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede o clique de fechar o botão imediatamente
    button.classList.toggle('active'); // Adiciona ou remove a classe 'active'
});

// Impede o fechamento do botão ao clicar no input number
salaInput.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

// Impede o fechamento ao interagir com o switch
mudarSwitch.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

// Impede o fechamento ao interagir com o switch
sendButton.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

// Altera o valor do input number para 1011 quando o switch é movido para a esquerda
mudarSwitch.addEventListener('change', function() {
    if (!mudarSwitch.checked) { // Verifica se o switch foi movido para a esquerda
        salaInput.value = 1011; // Define o valor para 1011
    }
});

// Verificação de tipo para esconder funções

// tipo 3

const usuarioLogadoHome = JSON.parse(sessionStorage.getItem('usuarioLogado'));

let tipoVer;

if (usuarioLogadoHome && usuarioLogadoHome.tipo !== undefined) {
    tipoVer = usuarioLogadoHome.tipo; // Atribui o valor dentro do 'if'
} else {
    console.error('Usuário inválido ou tipo não definido.');
}


    //tipo 3

    function toggleDisplay() {
        const btn = document.getElementById('btn');
        const attVer = document.getElementById('attVer');
        const monVer = document.getElementById('monVer');

        if (tipoVer == 3) {

            btn.style.display = 'none';
            attVer.style.display = 'none';
            monVer.style.display = 'none';
        }
    }

    toggleDisplay()
        
