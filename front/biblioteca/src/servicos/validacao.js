import axios from 'axios';

const api = axios.create({ baseURL: 'http://localhost:8080' });

async function validacao(login, senha) {
    const response = await api.post('/validacao', { login, senha });
    return response.data.token;  // Certifique-se de que a resposta contém o token corretamente
}

export {
    validacao
};