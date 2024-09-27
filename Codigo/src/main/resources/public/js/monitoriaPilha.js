document.addEventListener('DOMContentLoaded', () => {
    const menuButtons = document.querySelectorAll('.menu-hamburger');  // Seleciona todos os botões
    const menuDivs = document.querySelectorAll('.div-hamburguer');  // Seleciona todas as divs

    menuButtons.forEach((menuButton, index) => {
        const menuDiv = menuDivs[index];  // Pega a div correspondente ao botão

        menuButton.addEventListener('click', () => {
            // Adiciona ou remove a classe 'open' ao botão e à div correspondente
            menuButton.classList.toggle('open');
            menuDiv.classList.toggle('open');
        });
    });
});
