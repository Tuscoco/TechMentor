// document.addEventListener('DOMContentLoaded', () => {
//     // Seleciona todos os botões com a classe 'menu-hamburger'
//     const menuButtons = document.querySelectorAll('.menu-hamburger');  

//     // Itera sobre cada botão
//     menuButtons.forEach(menuButton => {
//         // Adiciona um evento de clique a cada botão
//         menuButton.addEventListener('click', () => {
//             // Adiciona ou remove a classe 'open' diretamente no botão
//             menuButton.classList.toggle('open');
//         });
//     });
// });




// isso é pro style caso eu use novamente
// document.addEventListener('DOMContentLoaded', () => {
//     const checkbox = document.getElementById('switch'); // Altera o ID para 'switch'
//     const gradeCSS = document.getElementById('grade-css');
//     const pilhaCSS = document.getElementById('pilha-css');

//     // Verifica o estado da checkbox e alterna os estilos CSS
//     checkbox.addEventListener('change', () => {
//         if (checkbox.checked) {
//             // Se marcado, desabilita o "monitoriaGrade" e habilita "monitoriaPilha"
//             gradeCSS.disabled = false;
//             pilhaCSS.disabled = true;
//         } else {
//             // Se desmarcado, habilita "monitoriaGrade" e desabilita "monitoriaPilha"
//             gradeCSS.disabled = true;
//             pilhaCSS.disabled = false;
//         }
//     });
// });