const monitorCache = {
    online: [],
    offline: [],
    timestamp: null,
};

// Função para atualizar o cache
export async function atualizarCache(url) {
    try {
        const onlineResponse = await fetch(`${url}/mostrarMonitoresOnline`);
        const offlineResponse = await fetch(`${url}/mostrarMonitoresOffline`);

        if (!onlineResponse.ok || !offlineResponse.ok) {
            throw new Error('Erro ao atualizar o cache.');
        }

        monitorCache.online = await onlineResponse.json();
        monitorCache.offline = await offlineResponse.json();
        monitorCache.timestamp = new Date().toISOString();

        console.log('Cache atualizado com sucesso.');
    } catch (error) {
        console.error('Erro ao atualizar o cache:', error);
    }
}

// Função para obter monitores do cache
export function obterCache(tipo) {
    return monitorCache[tipo] || [];
}
