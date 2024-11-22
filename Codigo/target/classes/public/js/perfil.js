const url = 'http://localhost:4567'; // Endereço do seu servidor
const usuarioLogadoPerfil = JSON.parse(localStorage.getItem('usuarioLogado'));
document.querySelector('#nameEdit').textContent = usuarioLogadoPerfil.nome;
document.querySelector('#senhaEdit').textContent = usuarioLogadoPerfil.senha;

document.getElementById('sair').addEventListener('click', function() {
    localStorage.clear();
    localStorage.clear();
    
    window.location.reload();
    window.location.href = 'logCad.html'; 
});

// Evento para o botão de enviar
document.getElementById('uploadButton').addEventListener('click', async function() {
    const imgUrl = await uploadImage();
    console.log(imgUrl);
    salvarFoto(usuarioLogadoPerfil.id, imgUrl); 
    location.reload();
});

async function uploadImage() {
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    
    if (!file) {
        alert('Por favor, selecione uma imagem primeiro.');
        return;
    }

    // Configurações de upload
    const cloudName = 'deycrrjpb'; // Substitua pelo seu Cloud name
    const uploadPreset = 'profileImg'; // Defina um Upload Preset no Cloudinary
    const folderName = 'profileImg'; // Substitua pelo nome da pasta desejada

    const urlCloudPerf = `https://api.cloudinary.com/v1_1/${cloudName}/image/upload`;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', uploadPreset);
    formData.append('folder', folderName); // Define a pasta para armazenar as imagens

    try {
        // Fazendo o upload da imagem para o Cloudinary
        const response = await fetch(urlCloudPerf, {
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

async function salvarFoto(id, urlFoto) {
    const data = {
        foto: urlFoto
    };

    try {
        const response = await fetch(`${url}/salvarfoto/${id}`, {
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