const url = 'http://localhost:4567'; // Endereço do seu servidor
const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));

if (usuarioLogado) {
    inicializarUsuario(usuarioLogado); // Chama a função para iniciar
} else {
    // Redireciona para login se o usuário não estiver logado
    alert('Faça login para acessar a página.');
    window.location.href = '../html/logCad.html'; // Ajuste para a URL correta da página de login
}

async function inicializarUsuario(usuarioLogado) {
    try {

        // Atualiza o sessionStorage com o objeto atualizado
        sessionStorage.setItem('usuarioLogado', JSON.stringify(usuarioLogado));
        
        document.querySelector('#username').textContent = usuarioLogado.nome;
        console.log(`Bem-vindo, ${usuarioLogado.nome}!`);

        // Exibe o nome no elemento com ID 'username'
    } catch (error) {
        console.error('Erro ao inicializar o usuário:', error);
    }
}

async function fetchAndDisplayImage() {
    try {
        // ID do usuário logado
        const id = usuarioLogado.id;

        // Faz o fetch para obter a URL da imagem
        const response = await fetch(`${url}/mostrarfoto/${id}`);
        
        if (!response.ok) {
            throw new Error('Erro ao buscar a imagem');
        }

        // Converte a resposta para JSON
        const foto = await response.json(); 
        const imageUrl = foto;

        // Atualiza todas as imagens com a classe 'profileImage'
        const imageElements = document.getElementsByClassName('profileImage');
        if (imageElements.length > 0) {
            for (const img of imageElements) {
                img.src = imageUrl;
            }
        } else {
            console.error('Elementos com a classe "profileImage" não encontrados.');
        }
    } catch (error) {
        console.error('Erro ao carregar a imagem de perfil:', error);
    }
}

fetchAndDisplayImage();