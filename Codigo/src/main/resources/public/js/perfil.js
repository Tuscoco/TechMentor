const url = 'http://localhost:4567'; // Endereço do seu servidor
const usuarioLogadoPerfil = JSON.parse(localStorage.getItem('usuarioLogado'));
document.querySelector('#nameEdit').textContent = usuarioLogadoPerfil.nome;
document.querySelector('#emailEdit').textContent = usuarioLogadoPerfil.email;

document.getElementById('sair').addEventListener('click', function() {
    localStorage.clear();
    localStorage.clear();
    
    window.location.reload();
    window.location.href = 'logCad.html'; 
});

document.getElementById('uploadButton').addEventListener('click', async function() {
    const imgUrl = await uploadImage();
    console.log(imgUrl);
    salvarFoto(usuarioLogadoPerfil.id, imgUrl); 
    location.reload();
});

document.getElementById('nameSubm').addEventListener('click', function() {
    const nome = document.getElementById('nome').value;

    if(nome){
        usuarioLogadoPerfil.nome = nome;
        localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogadoPerfil));
        alterarNome(usuarioLogadoPerfil.id, nome);  
        window.location.reload();
    }else{
        alert("insira um nome");
    }

});

document.getElementById('emailSubm').addEventListener('click', function() {
    const email = document.getElementById('email').value;

    if(email){
        // usuarioLogadoPerfil.email = email;
        // localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogadoPerfil));
        alterarEmail(usuarioLogadoPerfil.id, email);  
        window.location.reload();
    }else{
        alert("Insira um email")
    }
});

document.getElementById('senhaSubm').addEventListener('click', function() {
    const senha = document.getElementById('senha').value; 

    if(senha){
        usuarioLogadoPerfil.senha = senha;
        localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogadoPerfil));
        alterarSenha(usuarioLogadoPerfil.id, senha);  
        window.location.reload();
    }else{
        alert("Insira uma senha");
    }
});

document.getElementById('horaSubm').addEventListener('click', function() {
    const horaEnt = document.getElementById('entrada').value; 
    const horaSai = document.getElementById('saida').value; 

    if(horaEnt && horaSai){
        // usuarioLogadoPerfil.horario = horario;
        // localStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogadoPerfil));
        alterarHorarios(horaEnt, horaSai);  
        window.location.reload();
    }else{
        alert("Insira um horario");
    }
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

async function alterarNome(id, nome) {
    
    const data = {
        nome: nome,
        id: id
    };

    try {
        // Fazendo a requisição POST
        const response = await fetch(`${url}/alterarnome`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) // Convertendo o corpo da requisição para JSON
        });

        // Verificando se a requisição foi bem-sucedida
        if (response.ok) {
            const result = await response.json();
            console.log("Nome alterado com sucesso:", result);
            return result;
        } else {
            console.error("Erro ao alterar nome:", response.status, response.statusText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

async function alterarEmail(id, email) {

    const data = {
        email: email,
        id: id
    };

    try {
        // Fazendo a requisição POST
        const response = await fetch(`${url}/alteraremail`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) // Convertendo o corpo para JSON
        });

        // Verificando se a requisição foi bem-sucedida
        if (response.ok) {
            const result = await response.json();
            console.log("Email alterado com sucesso:", result);
            return result;
        } else {
            console.error("Erro ao alterar email:", response.status, response.statusText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

async function alterarSenha(id, senha) {

    const data = {
        senha: senha,
        id: id
    };

    try {
        // Fazendo a requisição POST
        const response = await fetch(`${url}/alterarsenha`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) // Convertendo o corpo para JSON
        });

        // Verificando se a requisição foi bem-sucedida
        if (response.ok) {
            const result = await response.json();
            console.log("Senha alterado com sucesso:", result);
            return result;
        } else {
            console.error("Erro ao alterar senha:", response.status, response.statusText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}

async function alterarHorarios(entrada, saida) {

    const data = {
        entrada: entrada,
        saida: saida
    };

    try {
        // Fazendo a requisição POST
        const response = await fetch(`${url}/sethorarios/${usuarioLogadoPerfil.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) // Convertendo o corpo para JSON
        });

        // Verificando se a requisição foi bem-sucedida
        if (response.ok) {
            const result = await response.json();
            console.log("Senha alterado com sucesso:", result);
            return result;
        } else {
            console.error("Erro ao alterar senha:", response.status, response.statusText);
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}