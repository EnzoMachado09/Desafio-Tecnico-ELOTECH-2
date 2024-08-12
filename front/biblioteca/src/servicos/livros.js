import axios from "axios";

const livrosAPI = axios.create({ baseURL: "http://localhost:8080/livros" });

async function getLivros(titulo, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await livrosAPI.get(`/titulo/${titulo}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data.content;
}

async function cadastrarLivro(dados, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    console.log('Dados enviados:', dados);
    const response = await livrosAPI.post("", dados, {
        headers: { 
            Authorization: `Bearer ${token}` }
    });
    return response.data;
}

async function atualizarLivro(dados, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    const response = await livrosAPI.put("", dados, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
}

async function deletarLivro(id, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    await livrosAPI.delete(`/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
    });
}

async function listarLivros(token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await livrosAPI.get("", {
        headers: { Authorization: `Bearer ${token}` }
    });

    return response.data.content;
}

export { getLivros, cadastrarLivro, atualizarLivro, deletarLivro, listarLivros };
