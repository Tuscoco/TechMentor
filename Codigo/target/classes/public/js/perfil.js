const usuarioLogadoPerfil = JSON.parse(sessionStorage.getItem('usuarioLogado'));

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

async function salvarFoto(userId, photoUrl) {
    try {   
                // Passo 2b: Se o usuário não possui uma foto, insira uma nova (POST)
                const insertResponse = await fetch(`http://localhost:4567/salvarfoto/${userId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ userId, photoUrl })
                });
                
                const insertData = await insertResponse.json();
                console.log('Foto salva com sucesso:', insertData.message);
         
    } catch (error) {
        console.error('Erro ao salvar ou atualizar a foto:', error);
    }
}