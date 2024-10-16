document.addEventListener('DOMContentLoaded', () => {
    const checkbox = document.getElementById('switch'); // Altera o ID para 'switch'
    const gradeCSS = document.getElementById('grade-css');
    const pilhaCSS = document.getElementById('pilha-css');

    // Verifica o estado da checkbox e alterna os estilos CSS
    checkbox.addEventListener('change', () => {
        if (checkbox.checked) {
            // Se marcado, desabilita o "monitoriaGrade" e habilita "monitoriaPilha"
            gradeCSS.disabled = true;
            pilhaCSS.disabled = false;
        } else {
            // Se desmarcado, habilita "monitoriaGrade" e desabilita "monitoriaPilha"
            gradeCSS.disabled = false;
            pilhaCSS.disabled = true;
        }
    });
});
