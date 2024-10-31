const button = document.getElementById('btn');
const salaInput = document.getElementById('sala');
const mudarSwitch = document.getElementById('switch');
const sendButton = document.getElementById('sendBtn');
const usuarioLogadoHome = JSON.parse(sessionStorage.getItem('usuarioLogado'));

const tipoVer = usuarioLogadoHome.tipo;
toggleDisplay(tipoVer);



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



// Função para alternar a exibição com base no tipo de usuário
function toggleDisplay(tipoVer) {
     
    const btn = document.getElementById('btn');
    const attVer = document.getElementById('attVer');
    const monVer = document.getElementById('monVer');

    if (tipoVer == 3) {
        btn.style.display = 'none';
        attVer.style.display = 'none';
        monVer.style.display = 'none';
    } else if (tipoVer == 2) {
        monVer.style.display = 'none';
    } else if (tipoVer == 1) {
        btn.style.display = 'none';
    }
}

// Verifica o status do monitor assim que o DOM estiver carregado
document.addEventListener("DOMContentLoaded", async () => {
    const status = await verificarStatusMonitor(usuarioLogadoHome.id);
    console.log("Status do monitor:", status);

    // Atualiza o estado do switch baseado no status
    const switchElement = document.getElementById('switch');
    switchElement.checked = (status === 1);
});

// Evento do switch para alternar entre online e offline
mudarSwitch.addEventListener('change', () => {
    if (mudarSwitch.checked) {
        // Quando o switch é ligado
        funcaoLigada(usuarioLogadoHome.id);
        // document.getElementById("sala").value = sala; // Define o valor no campo de entrada

    } else {
        // Quando o switch é desligado
        mudarSwitch.addEventListener('change', function() {
            if (!mudarSwitch.checked) { // Verifica se o switch foi movido para a esquerda
                salaInput.value = 1011; // Define o valor para 1011
            }
        });
        funcaoDesligada(usuarioLogadoHome.id);
    }
})

// Função para verificar o status do monitor
async function verificarStatusMonitor(id) {
    try {
        console.log("ID do monitor:", id); // Log do ID

        const response = await fetch(`http://localhost:4567/mostraronline/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) throw new Error("Erro ao verificar status do monitor");

        const data = await response.json(); // Obtenha os dados da resposta
        if(data == true){
            return 1;
        }
        else{
            return 0;
        }
    } catch (error) {
        console.error("Erro ao verificar status do monitor:", error);
        return 0; // Retorna 0 em caso de erro
    }
}

// Função para quando o switch é ligado
function funcaoLigada(id) {
    
    
    fetch(`http://localhost:4567/ficaronline/${id}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Erro na solicitação");
        }
    })
    .then(data => {
        console.log("Resposta do servidor:", data);
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

// Função para quando o switch é desligado
function funcaoDesligada(id) {
    fetch(`http://localhost:4567/ficaroffline/${id}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Erro na solicitação");
        }
    })
    .then(data => {
        console.log("Resposta do servidor:", data);
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

async function mudarSala(id, sala) {
    const url = `http://localhost:4567/mudarsala/${id}`;
    const dados = { sala: sala };
    
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dados)
        });
        
        if (response.ok) {
            const respostaJson = await response.json();
            console.log("Sala alterada com sucesso:", respostaJson);
        } else {
            console.error("Erro ao mudar de sala:", response.statusText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

// async function mostrarSala(idAluno) {
//     const url = `http://localhost:4567/mostrarsala/${idAluno}`;
    
//     try {
//         const response = await fetch(url, {
//             method: "GET"
//         });
        
//         if (response.ok) {
//             const dados = await response.json();
//             // Verifica se a sala está definida e é um número válido
//             if (dados.sala !== undefined && !isNaN(dados.sala)) {
//                 document.getElementById("sala").value = dados.sala;
//             } else {
//                 console.error("Valor da sala não definido ou inválido:", dados.sala);
//                 document.getElementById("sala").value = ''; // Limpa o campo se o valor for inválido
//             }
//         } else {
//             console.error("Erro ao obter dados da sala:", response.statusText);
//         }
//     } catch (error) {
//         console.error("Erro na requisição:", error);
//     }
// }


// Exemplo de uso
// const salaInput = document.getElementById('sala');
// const valorSala = salaInput.value;
// mudarSala(usuarioLogadoHome.id, valorSala);
// mudarSala(usuarioLogadoHome.id, valorSala);
