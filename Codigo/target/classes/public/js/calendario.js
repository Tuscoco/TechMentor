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
                    loadEvents(day, month + 1); // Carregar eventos para o dia selecionado
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

// Função para abrir o modal e escurecer o fundo
function openModal() {
    const modal = document.getElementById('modal');
    modal.style.display = 'flex'; // Mostra a janela modal
    modal.style.opacity = 0; // Inicialmente opaco
    document.body.style.overflow = 'hidden'; // Evita rolar a página ao fundo

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

// Adicionar evento ao botão "Adicionar"
document.getElementById('novoEvento').querySelector('button').addEventListener('click', () => {
    const eventoInput = document.getElementById('novoEvento').querySelector('input[type="text"]');
    const horaInput = document.getElementById('novoEvento').querySelector('input[type="time"]');

    const evento = eventoInput.value;
    const hora = horaInput.value;

    if (evento && hora) {
        const dataToSend = {
            id: generateId(), // Função para gerar um ID único
            evento: evento,
            dia: day,
            mes: mes + 1, // Adiciona 1 porque os meses começam em 0
            hora: hora
        };

        // Enviar dados para o JSON Server
        fetch('http://localhost:3000/eventos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataToSend)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Evento adicionado:', data);
            closeModal(); // Fecha o modal após adicionar o evento
            eventoInput.value = ''; // Limpa o campo de evento
            horaInput.value = ''; // Limpa o campo de hora
            loadEvents(day, mes + 1); // Carregar eventos atualizados após adicionar
        })
        .catch(error => {
            console.error('Erro ao adicionar evento:', error);
        });
    } else {
        alert('Por favor, preencha todos os campos.');
    }
});

// Função para gerar um ID único
function generateId() {
    return Math.random().toString(36).substr(2, 8); // Gera um ID aleatório
}

// Função para carregar eventos do JSON Server e exibir na div de mostrarEventos
function loadEvents(selectedDay, selectedMonth) {
    const mostrarEventosDiv = document.getElementById('mostrarEventos');
    mostrarEventosDiv.innerHTML = ''; // Limpa eventos anteriores

    fetch(`http://localhost:3000/eventos?dia=${selectedDay}&mes=${selectedMonth}`)
        .then(response => response.json())
        .then(events => {
            events.forEach(event => {
                const eventElement = document.createElement('p');
                eventElement.innerHTML = `${event.evento} - ${event.hora} 
                    <div class="apagar" data-id="${event.id}">
                        <i id="trash" class="ph-fill ph-trash-simple"></i>
                        <i id="recicle" class="ph-fill ph-recycle"></i>
                    </div>`; // Formato desejado

                // Adicionar o evento de clique ao ícone de exclusão
                const deleteIcon = eventElement.querySelector('.apagar');
                deleteIcon.addEventListener('click', () => {
                    deleteEvent(event.id);
                });

                mostrarEventosDiv.appendChild(eventElement);
            });

            if (events.length === 0) {
                const noEventsMessage = document.createElement('p');
                noEventsMessage.innerHTML = 'Nenhum evento encontrado para hoje.';
                mostrarEventosDiv.appendChild(noEventsMessage);
            }
        })
        .catch(error => {
            console.error('Erro ao carregar eventos:', error);
        });
}

// Função para deletar um evento
function deleteEvent(eventId) {
    fetch(`http://localhost:3000/eventos/${eventId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            console.log('Evento deletado:', eventId);
            // Recarregar os eventos para atualizar a lista
            loadEvents(day, mes + 1);
        } else {
            console.error('Erro ao deletar evento:', response.statusText);
        }
    })
    .catch(error => {
        console.error('Erro ao deletar evento:', error);
    });
}

const usuarioLogadoHome = JSON.parse(sessionStorage.getItem('usuarioLogado'));

let tipoVer;

if (usuarioLogadoHome && usuarioLogadoHome.tipo !== undefined) {
    tipoVer = usuarioLogadoHome.tipo; // Atribui o valor dentro do 'if'
} else {
    console.error('Usuário inválido ou tipo não definido.');
}

    //tipo 3

    function toggleDisplay() {
        const novoEvento = document.getElementById('novoEvento');

        if (tipoVer == 3) {

            novoEvento.style.display = 'none';
        }
    }

    toggleDisplay()
        