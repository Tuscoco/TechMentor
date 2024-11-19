const button = document.getElementById('btn');
const salaInput = document.getElementById('sala');
const mudarSwitch = document.getElementById('switch');
const sendButton = document.getElementById('sendBtn');
const usuarioLogadoHome = JSON.parse(sessionStorage.getItem('usuarioLogado'));
const tipoVer = usuarioLogadoHome.tipo;
const verFotoElement = document.getElementById('verFoto');
const video = document.getElementById('video');
const captureButton = document.getElementById('captureButton');
const loadingButton = document.getElementById('loadingButton');

let cameraStream = null;



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

verFotoElement.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});




async function start() {

    captureButton.style.display = 'block';
    loadingButton.style.display = 'none';

    const labeledFaceDescriptors = await loadLabeledImages();
    const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors, 0.6);
    // document.body.append('Loaded');

    captureButton.addEventListener('click', async () => {
    if (!cameraStream) {
        alert('Please turn on the camera first!');
        return;
    }

    // Tira foto
    const image = await captureImage(video);

    // Detecta se tem caras
    const detections = await faceapi.detectAllFaces(image).withFaceLandmarks().withFaceDescriptors();


    const faceFound = detections.length > 0;
    status.textContent = faceFound ? "Face(s) detected" : "No faces detected";

    // Detecta se o zé ta la
    const results = detections.map(d => faceMatcher.findBestMatch(d.descriptor));
    const isFaceMatched = results.some(result => result.label !== "unknown");

    // if (results.length > 0) {
    //   labelDetected.textContent = "Detected: " + results.map(result => result.toString()).join(', ');
    //   console.log("Match found:", isFaceMatched);
    // } else {
    //   labelDetected.textContent = "No known faces detected";
    // }

    console.log("Face matched with known label:", isFaceMatched);
    });
}

function captureImage(videoElement) {
    const canvas = document.createElement('canvas');
    canvas.width = videoElement.videoWidth;
    canvas.height = videoElement.videoHeight;

    const context = canvas.getContext('2d');
    context.drawImage(videoElement, 0, 0, canvas.width, canvas.height);

    const image = new Image();
    image.src = canvas.toDataURL('image/png');

    return new Promise(resolve => {
        image.onload = () => resolve(image);
    });
}

async function loadLabeledImages() {
    const monitorId = 1527678; // Hardcoded ID for Felipe Portes
    const label = 'Felipe Portes'; // Label for the monitor
    const descriptions = [];
  
    // Fetch the photos for Felipe Portes by their ID
    const imageUrls = await fetchPhotoMonitorData(monitorId);
    console.log(imageUrls);
  
    for (const url of imageUrls) {
        try {
            const img = await faceapi.fetchImage(url);
            const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor();
  
            if (detections) {
                descriptions.push(detections.descriptor);
            }
        } catch (error) {
            console.error(`Error processing image from URL: ${url}`, error);
        }
    }
  
    // Returning a single LabeledFaceDescriptors object for Felipe Portes
    return [
        new faceapi.LabeledFaceDescriptors(label, descriptions)
    ];
}

async function fetchPhotoMonitorData(id) {
try {
    const response = await fetch(`http://localhost:4567/mostrarfotomonitor/${id}`);
    
    if (!response.ok) {
    throw new Error('Network response was not ok');
    }
    
    const data = await response.json();
    return data; // assuming the response is an array of strings
} catch (error) {
    console.error('Fetch error:', error);
    return [];
}
}
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
mudarSwitch.addEventListener('change', async () => {
    
    
    if (mudarSwitch.checked) {
        
        
        verFotoElement.style.display = 'flex';
        cameraStream = await navigator.mediaDevices.getUserMedia({ video: true });
        video.srcObject = cameraStream;
        video.play();
        // funcaoLigada(usuarioLogadoHome.id);
        
    } else {
        
        verFotoElement.style.display = 'none';
        // funcaoDesligada(usuarioLogadoHome.id);
    }
})

Promise.all([
    faceapi.nets.faceRecognitionNet.loadFromUri('../models'),
    faceapi.nets.faceLandmark68Net.loadFromUri('../models'),
    faceapi.nets.ssdMobilenetv1.loadFromUri('../models')
  ]).then(start);


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
