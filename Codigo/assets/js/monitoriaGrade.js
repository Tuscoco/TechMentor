
const button = document.getElementsById('monitor');

function openModal() {
    const modal = document.getElementById('modal');
    modal.style.display = 'flex'; // Mostra a janela modal
    modal.style.opacity = 0; // Inicialmente opaco
    document.body.style.overflow = 'hidden'; // Evita rolar a página ao fundo

    setTimeout(() => {
        modal.style.opacity = 1; // Aumenta a opacidade gradualmente
    }   , 10);
}

function closeModal() {

    //desmarca o botao selecionado
    // const selectedButton = document.querySelector('.selected');
    // if (selectedButton) {
    //     selectedButton.classList.remove('selected');
    // }
    
    const modal = document.getElementById('modal');
    modal.style.opacity = 0; // Inicia o fade out

    // Aguardar a transição de opacidade antes de ocultar o modal
    setTimeout(() => {
        modal.style.display = 'none'; // Oculta a janela modal
        document.body.style.overflow = 'auto'; // Restaura a rolagem da página
    }, 250); // O tempo deve ser o mesmo definido para a transição no CSS
}

button.addEventListener('click', () => {
    eventos.innerHTML = "";
    openModal();
});

// Fecha o modal ao clicar no X
document.getElementById('closeModal').addEventListener('click', closeModal);

// Fecha o modal ao clicar fora dele
window.addEventListener('click', (event) => {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        closeModal();
    }
});