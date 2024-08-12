import axios from 'axios';

const recAPI = axios.create({ baseURL: "http://localhost:8080/recomendacoes" });

async function getRecomendacoesPorUsuario (idUsuario, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await recAPI.get(`/${idUsuario}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return response.data;

};

export { getRecomendacoesPorUsuario };
