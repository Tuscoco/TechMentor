const url = 'http://localhost:4567'; // Endereço do seu servidor
const button = document.getElementById('btn');
const mudarSwitch = document.getElementById('switch');
const sendButton = document.getElementById('sendBtn');
const usuarioLogadoHome = JSON.parse(localStorage.getItem('usuarioLogado'));
const tipoVer = usuarioLogadoHome.tipo;
const verFotoElement = document.getElementById('verFoto');
const video = document.getElementById('video');
const captureButton = document.getElementById('captureButton');
const loadingButton = document.getElementById('loadingButton');
const tirarFoto = document.getElementById('tirarFoto');
const enviarSala = document.getElementById('send')

let cameraStream = null;

toggleDisplay(tipoVer);
mostrarSala();

// Alterna a classe 'active' ao clicar no botão
button.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede o clique de fechar o botão imediatamente
    button.classList.toggle('active'); // Adiciona ou remove a classe 'active'
});

// Impede o fechamento do botão ao clicar no input number
enviarSala.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

sendButton.addEventListener('click', function() {
    const salaInput = document.getElementById('sala').value; // Obtém o valor atualizado no momento do clique
    mudarSala(salaInput); // Passa o valor para a função mudarSala
});

// Impede o fechamento ao interagir com o switch
mudarSwitch.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

verFotoElement.addEventListener('click', function(event) {
    event.stopPropagation(); // Impede a propagação do evento de clique
});

async function start() {

    const labeledFaceDescriptors = await loadLabeledImages();
    const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors, 0.6);
    // document.body.append('Loaded');

    captureButton.style.display = 'block';
    loadingButton.style.display = 'none';

    captureButton.addEventListener('click', async () => {
        if (!cameraStream) {
            alert('Por favor ligue sua camera primeiro!');
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

        console.log("Face matched with known label:", isFaceMatched);
        if(isFaceMatched){
            matchEnd();
        }
        else{
            unmatchEnd();
            
        }

    });
}

function matchEnd(){
    console.log("verificado");

    captureButton.style.backgroundColor = "green";
    setTimeout(() => {
        cameraStream.getTracks().forEach(track => track.stop());
        video.srcObject = null;
        cameraStream = null;
        verFotoElement.style.display = 'none';
    }, 1000);
    funcaoLigada(usuarioLogadoHome.id);
    mostrarSala();

}

function unmatchEnd(){
    console.log("n verificado");
    captureButton.style.backgroundColor = "red";
}

async function uploadImage(foto) {

    if (!foto) {
        alert('Por favor, selecione uma imagem primeiro.');
        return;
    }

    // Configurações de upload
    const cloudName = 'deycrrjpb'; // Substitua pelo seu Cloud name
    const uploadPreset = 'verificationImg'; // Defina um Upload Preset no Cloudinary
    const folderNameVer = 'verificationImg'; // Substitua pelo nome da pasta desejada

    const urlCloud = `https://api.cloudinary.com/v1_1/${cloudName}/image/upload`;

    const formData = new FormData();
    formData.append('file', foto);
    formData.append('upload_preset', uploadPreset);
    formData.append('folder', folderNameVer); // Define a pasta para armazenar as imagens

    try {
        // Fazendo o upload da imagem para o Cloudinary
        const response = await fetch(urlCloud, {
            method: 'POST',
            body: formData
        });
        const data = await response.json();

        if (data.secure_url) {
            // Exibindo a URL e a imagem carregada
            return data.secure_url;
            
            
        } else {
            console.error('Erro ao fazer upload:', data);
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
}

async function capturarImagens() {
    return new Promise((resolve) => {
        tirarFoto.addEventListener('click', async () => {
            const image = await captureImage(video);
            const arquivoFoto1 = await imageToFile(image, 'minha_foto.png');
            resolve(arquivoFoto1);
        });
    });
}

function imageToFile(image, fileName = 'captured_image.png') {
    return new Promise((resolve) => {
        // Cria um elemento canvas
        const canvas = document.createElement('canvas');
        canvas.width = image.width;
        canvas.height = image.height;

        // Desenha a imagem no canvas
        const context = canvas.getContext('2d');
        context.drawImage(image, 0, 0, canvas.width, canvas.height);

        // Converte o canvas para um Blob
        canvas.toBlob((blob) => {
            // Converte o Blob para um File
            const file = new File([blob], fileName, { type: 'image/png' });
            resolve(file);
        }, 'image/png');
    });
}

async function postarFoto(){

    loadingButton.style.display = 'none';
    tirarFoto.style.display = 'block';

    try {
        
        const foto1 = await capturarImagens();
        console.log('Primeira foto capturada:', foto1);

        loadingButton.style.display = 'block';
        tirarFoto.style.display = 'none';

        const urlFoto1 = await uploadImage(foto1);
        console.log('Primeira foto upada:', urlFoto1);

        loadingButton.style.display = 'none';
        tirarFoto.style.display = 'block';

        const foto2 = await capturarImagens();
        console.log('Segunda foto capturada:', foto2);

        loadingButton.style.display = 'block';
        tirarFoto.style.display = 'none';

        const urlFoto2 = await uploadImage(foto2);
        console.log('Segunda foto upada:', urlFoto2);
        
        const data = {
            foto1: urlFoto1,
            foto2: urlFoto2
        };
    
        const response = await fetch(`${url}/salvarfotomonitor/${usuarioLogadoHome.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Foto salva com sucesso:', result);
            return result;
        } else {
            console.error('Erro ao salvar foto:', response.status, response.statusText);
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
    }
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
    const monitorId = usuarioLogadoHome.id; // Hardcoded ID for Felipe Portes
    const label = usuarioLogadoHome.nome; // Label for the monitor
    const descriptions = [];
  
    // Fetch the photos for Felipe Portes by their ID
    let imageUrls = await fetchPhotoMonitorData(monitorId);
    console.log(imageUrls);

    if(imageUrls[0] == null || imageUrls[0] == null){
        await postarFoto();   
        imageUrls = await fetchPhotoMonitorData(monitorId);
        console.log(imageUrls);     
    }
  
    for (let imageUrl of imageUrls) {
        try {
            const img = await faceapi.fetchImage(imageUrl);
            const detections = await faceapi.detectSingleFace(img).withFaceLandmarks().withFaceDescriptor();
  
            if (detections) {
                descriptions.push(detections.descriptor);
            }
        } catch (error) {
            console.error(`Error processing image from URL: ${imageUrl}`, error);
        }
    }
  
    // Returning a single LabeledFaceDescriptors object for Felipe Portes
    return [
        new faceapi.LabeledFaceDescriptors(label, descriptions)
    ];
}

async function fetchPhotoMonitorData(id) {
try {
    const response = await fetch(`${url}/mostrarfotomonitor/${id}`);
    
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
    const attVerCord = document.getElementById('attVerCord');
    const monVer = document.getElementById('monVer');

    if (tipoVer == 3) {
        btn.style.display = 'none';
        attVer.style.display = 'none';
        monVer.style.display = 'none';
        attVerCord.style.display = 'none';
    } else if (tipoVer == 2) {
        attVerCord.style.display = 'none';
        monVer.style.display = 'none';
    } else if (tipoVer == 1) {
        btn.style.display = 'none';
        attVer.style.display = 'none';
    }
}

// Verifica o status do monitor assim que o DOM estiver carregado
document.addEventListener("DOMContentLoaded", async () => {
    const status = await verificarStatusMonitor(usuarioLogadoHome.id);
    console.log("Status do monitor:", status);

    // Atualiza o estado do switch baseado no status
    const switchElement = document.getElementById('switch');
    if(status == 1){
        switchElement.checked = true;
        enviarSala.style.display = 'flex';
    }
    else{
        enviarSala.style.display = 'none';
    }

});

// Evento do switch para alternar entre online e offline
mudarSwitch.addEventListener('change', async () => {
    
    if (mudarSwitch.checked) {
        
        verFotoElement.style.display = 'flex';
        enviarSala.style.display = 'flex';
        cameraStream = await navigator.mediaDevices.getUserMedia({ video: true });
        video.srcObject = cameraStream;
        const verProm = await doPromise();
        
        if(verProm){
            start();
        }
        
    } else {

        captureButton.style.backgroundColor = "#7F32A6";
        verFotoElement.style.display = 'none';
        enviarSala.style.display = 'none';
        funcaoDesligada(usuarioLogadoHome.id);
    }
})

async function doPromise() {
    try {
        await Promise.all([
            faceapi.nets.faceRecognitionNet.loadFromUri('../models'),
            faceapi.nets.faceLandmark68Net.loadFromUri('../models'),
            faceapi.nets.ssdMobilenetv1.loadFromUri('../models')
        ]);

        return true;  // Retorno após o carregamento bem-sucedido dos modelos
    } catch (error) {
        return false;  // Retorno em caso de erro
    }
}
  
// Função para verificar o status do monitor
async function verificarStatusMonitor(id) {
    try {
        console.log("ID do monitor:", id); // Log do ID

        const response = await fetch(`${url}/mostraronline/${id}`, {
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
    
    fetch(`${url}/ficaronline/${id}`, {
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
    fetch(`${url}/ficaroffline/${id}`, {
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

async function mostrarSala() {    
    try {
        // Fazendo a requisição GET
        const response = await fetch(`${url}/mostrarsala/${usuarioLogadoHome.id}`, { method: 'GET' });
        
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }
        
        // Obtendo o texto da resposta
        const data = await response.json();
        
        
        // Preenchendo o campo de texto com o valor retornado
        const inputSala = document.getElementById('sala');
        if (inputSala) {
            inputSala.value = data;
        } else {
            console.error("Elemento com ID 'sala' não encontrado.");
        }
    } catch (error) {
        console.error("Erro ao buscar a sala:", error);
    }
}

async function mudarSala(sala) {
    
    try {
        const data = { 
            sala: sala
        };

        // Fazendo a requisição POST
        const response = await fetch(`${url}/mudarsala/${usuarioLogadoHome.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        // Verificando o status da resposta
        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.statusText}`);
        }

        // Obtendo a resposta em texto ou JSON
        const resposta = await response.json();
        console.log("Resposta do servidor:", resposta);
    } catch (error) {
        console.error("Erro ao mudar a sala:", error);
    }
}