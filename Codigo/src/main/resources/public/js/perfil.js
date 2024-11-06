const usuarioLogadoPerfil = JSON.parse(sessionStorage.getItem('usuarioLogado'));
fetchAndDisplayImage();
document.querySelector('#nameEdit').textContent = usuarioLogadoPerfil.nome;
document.querySelector('#senhaEdit').textContent = usuarioLogadoPerfil.senha;

document.getElementById('sair').addEventListener('click', function() {
    localStorage.clear();
    sessionStorage.clear();
    
    window.location.reload();
    window.location.href = 'logCad.html'; 
});

// Evento para o botão de enviar
document.getElementById('uploadButton').addEventListener('click', async function() {
    const imgUrl = await uploadImage();
    console.log(imgUrl);
    salvarFoto(usuarioLogadoPerfil.id, imgUrl); 
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
    const uploadPreset = 'profile_upload'; // Defina um Upload Preset no Cloudinary

    const url = `https://api.cloudinary.com/v1_1/${cloudName}/image/upload`;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', uploadPreset);

    try {
        // Fazendo o upload da imagem para o Cloudinary
        const response = await fetch(url, {
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

async function salvarFoto(userId, fotoUrl) {
    try {   

        const fotoUsuario = {
            id: userId,
            foto: fotoUrl
        };

        const response = await fetch(`http://localhost:4567/salvarfoto/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(fotoUsuario)
        });
        
        if (!response.ok) {
            // Exibe uma mensagem de erro se o status da resposta não for "ok"
            console.error('Erro na resposta do servidor:', response.status, response.statusText);
            return;
        }

        const data = await response.json();
        console.log('Foto salva com sucesso:', data.message);

    } catch (error) {
        console.error('Erro ao salvar ou atualizar a foto:', error);
    }
}


async function fetchAndDisplayImage() {
    try {
        const response = await fetch('http://localhost:4567/mostrarfoto/1525533');
        
        if (!response.ok) {
            throw new Error('Erro ao buscar a imagem');
        }

        const data = await response.json(); // Converte a resposta para JSON
        const imageUrlBase64 = data.foto; // Acessa a URL em Base64
        const imageUrl = atob(imageUrlBase64); // Decodifica a URL

        document.getElementById('profileImage').src = imageUrl;
    } catch (error) {
        console.error('Erro:', error);
    }
}