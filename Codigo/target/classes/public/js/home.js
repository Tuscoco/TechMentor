const button = document.getElementById('btn');
const salaInput = document.getElementById('sala');
const mudarSwitch = document.getElementById('mudar');

// Alterna a classe 'active' ao clicar no botão
button.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede o clique de fechar o botão imediatamente
    button.classList.toggle('active'); // Adiciona ou remove a classe 'active'
});

// Impede o fechamento do botão ao clicar no input number
salaInput.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

// Altera o valor do input number para 1011 quando o switch é movido para a esquerda
mudarSwitch.addEventListener('change', function() {
    if (!mudarSwitch.checked) { // Verifica se o switch foi movido para a esquerda
        salaInput.value = 1011; // Define o valor para 1011
    }

    // if(salaInput.value == null){
    //     salaInput.value = 1011; // Define o valor para 1011
    // }
});
