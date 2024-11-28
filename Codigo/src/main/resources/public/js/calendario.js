const url = 'https://plmg-cc-ti2-2024-2-g20-techmentor-1.onrender.com'; // Endereço do seu servidor ${url}
let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let day;
let mes;



// Gerar calendário ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    generateCalendar(currentMonth, currentYear); // Gerar o calendário para o mês atual
});

// Função para gerar o calendário
function generateCalendar(month, year) {    
    mes = month;
    const monthYearElement = document.getElementById('monthYear');
    monthYearElement.innerText = new Date(year, month).toLocaleString('default', { month: 'long', year: 'numeric' });

    const calendarBody = document.getElementById('calendarBody');
    calendarBody.innerHTML = '';

    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const daysInPrevMonth = new Date(year, month, 0).getDate();

    let date = 1;
    let nextDate = 1;
    let hasCurrentMonthDays = false;

    for (let i = 0; i < 6; i++) {
        const row = document.createElement('tr');
        hasCurrentMonthDays = false; // Reset for each row

        for (let j = 0; j < 7; j++) {
            const cell = document.createElement('td');

            if (i === 0 && j < firstDay) {
                const prevMonthDate = daysInPrevMonth - firstDay + j + 1;
                const button = document.createElement('button');
                button.innerText = prevMonthDate;
                button.classList.add('prev-month');
                button.disabled = true; // Optional: Disable buttons for previous month
                cell.appendChild(button);
            } else if (date > daysInMonth) {
                const button = document.createElement('button');
                button.innerText = nextDate;
                button.classList.add('next-month');
                button.disabled = true; // Optional: Disable buttons for next month
                cell.appendChild(button);
                nextDate++;
            } else {
                const button = document.createElement('button');
                button.innerText = date;

                // Verifica se o dia atual é igual ao dia gerado e destaca com a cor azul
                if (date === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                    button.style.backgroundColor = '#5932A6'; // Define a cor de fundo azul para o dia atual
                    button.style.color = 'white'; // Opcional: Define a cor do texto como branca para melhor contraste
                }

                button.addEventListener('click', () => {
                    const selectedButton = document.querySelector('.selected');
                    if (selectedButton) {
                        selectedButton.classList.remove('selected');
                    }
                    button.classList.add('selected');
                    day = +button.textContent;
                    openModal();
                });

                cell.appendChild(button);
                date++;
                hasCurrentMonthDays = true; // Set to true when there's a day from the current month
            }
            row.appendChild(cell);
        }
        // Only append the row if it contains at least one current month day
        if (hasCurrentMonthDays) {
            calendarBody.appendChild(row);
        }
    }
}

// Função para avançar para o próximo mês
function nextMonth() {
    const formContainer = document.getElementById('formContainer');
    formContainer.style.display = 'none';
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    generateCalendar(currentMonth, currentYear);
}

// Função para voltar para o mês anterior
function prevMonth() {
    const formContainer = document.getElementById('formContainer');
    formContainer.style.display = 'none';
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    generateCalendar(currentMonth, currentYear);
}

// Adicionar event listeners para os botões de próxima e anterior mês
document.getElementById('nextMonth').addEventListener('click', nextMonth);
document.getElementById('prevMonth').addEventListener('click', prevMonth);

// Função para abrir o modal e capturar o dia selecionado
function openModal() {
    const modal = document.getElementById('modal');
    modal.style.display = 'flex'; // Mostra a janela modal
    modal.style.opacity = 0; // Inicialmente opaco
    document.body.style.overflow = 'hidden'; // Evita rolar a página ao fundo
    // Exemplo de chamada da função ao clicar em uma data
    
    mostrarEventosPorData();
    setTimeout(() => {
        modal.style.opacity = 1; // Aumenta a opacidade gradualmente
    }, 10);
}

// Função para fechar o modal
function closeModal() {
    const modal = document.getElementById('modal');
    modal.style.opacity = 0; // Inicia o fade out

    // Aguardar a transição de opacidade antes de ocultar o modal
    setTimeout(() => {
        modal.style.display = 'none'; // Oculta a janela modal
        document.body.style.overflow = 'auto'; // Restaura a rolagem da página
    }, 250); // O tempo deve ser o mesmo definido para a transição no CSS
}

// Event listener para fechar o modal ao clicar no botão "X"
document.getElementById('closeModal').addEventListener('click', closeModal);

// Fecha o modal ao clicar fora dele
window.addEventListener('click', (event) => {
    const modal = document.getElementById('modal');
    if (event.target === modal) {
        closeModal();
    }
});

//Posta evento
document.querySelector('#novoEvento button').addEventListener('click', () => {
    const nome = document.getElementById('evento').value;
    const local = document.getElementById('local').value;
    const data = `${currentYear}-${String(mes + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`
    const hora = document.getElementById('hora').value;
    const materia = document.getElementById('materia').value;

    if (!nome) {
        alert('Por favor, insira um evento.');
        return;
    }

    // Dados para enviar ao servidor
    const eventoData = {
        nome: nome,
        local: local,
        data: data,
        hora: hora,
        materia: materia
    };

    // Enviar dados para o servidor
    fetch(`${url}/salvarevento`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(eventoData),
    })
    .then(response => {
        if (response.ok) {
            console.log('Evento salvo com sucesso');
            clearEventInput();
        } else {
            console.error('Falha ao salvar o evento');
        }
    })
    .catch((error) => {
        console.error('Erro ao salvar o evento:', error);
    });
});

function clearEventInput(){
    const eventoInput = document.getElementById('evento');
    const localInput = document.getElementById('local');
    const dataInput = document.getElementById('data');
    const horaInput = document.getElementById('hora');

    if (eventoInput) eventoInput.value = '';
    if (localInput) localInput.value = '';
    if (dataInput) dataInput.value = '';
    if (horaInput) horaInput.value = '';
}

// Função para recuperar eventos do banco de dados para uma data específica
function mostrarEventosPorData() {
    const dataAtual = `${currentYear}-${String(mes + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`

    // Faz a requisição GET para o servidor
    fetch(`${url}/mostrareventosdodia/${dataAtual}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(eventos => {
            const eventosDiv = document.getElementById('mostrarEventos');
            eventosDiv.innerHTML = '';
            
            if (eventos.length > 0) {
                eventos.forEach(evento => {

                    // Cria um elemento para cada evento e adiciona à div
                    const eventoElemento = document.createElement('p');
                    eventoElemento.innerHTML = `
                    <div class="dados">
                        ${evento.nome} - ${evento.hora} - ${evento.local}
                    </div>
                    <div class="apagar" data-id="${evento.id}"> <!-- Adicionando data-id com o ID do evento -->
                        <i id="trash" class="ph-fill ph-trash-simple"></i>
                        <i id="recicle" class="ph-fill ph-recycle"></i>
                    </div>`;
                    eventosDiv.appendChild(eventoElemento);
                });

                toggleDisplay()

                // Adiciona event listener para cada div de apagar
                const apagarDivs = document.querySelectorAll('.apagar');
                apagarDivs.forEach(div => {
                    div.addEventListener('click', (event) => {
                        const eventoId = div.getAttribute('data-id'); // Obtém o ID do evento
                        deletarEvento(eventoId); // Chama a função para deletar o evento
                    });
                });
            } else {
                eventosDiv.textContent = 'Nenhum evento encontrado para esta data.';
            }
        })
        .catch(error => {
            console.error('Houve um problema com a requisição:', error);
        });
}

function deletarEvento(eventoId) {
    fetch(`${url}/removerevento/${eventoId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            console.log('Evento deletado com sucesso');
            mostrarEventosPorData(); // Atualiza a lista de eventos após a remoção
        } else {
            console.error('Falha ao deletar o evento');
        }
    })
    .catch(error => {
        console.error('Erro ao deletar o evento:', error);
    });
}



//ESCONDER DEPENDENDO DO USUARIO
const usuarioLogadoHome = JSON.parse(localStorage.getItem('usuarioLogado'));

let tipoVer;

if (usuarioLogadoHome && usuarioLogadoHome.tipo !== undefined) {
    tipoVer = usuarioLogadoHome.tipo; // Atribui o valor dentro do 'if'
} else {
    console.error('Usuário inválido ou tipo não definido.');
}

//tipo 3
function toggleDisplay() {
    const novoEvento = document.getElementById('novoEvento');
    const elementosApagar = document.getElementsByClassName('apagar');

    if (tipoVer == 3) {
        novoEvento.style.display = 'none';

        Array.from(elementosApagar).forEach(elemento => {
            elemento.style.display = 'none';
        });
    }
}        