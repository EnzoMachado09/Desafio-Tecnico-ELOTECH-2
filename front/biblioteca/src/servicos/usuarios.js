import axios from "axios";

const usuariosAPI = axios.create({ baseURL: "http://localhost:8080/usuarios" });

async function getUsuarios(nome, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await usuariosAPI.get(`/nome/${nome}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data.content;
}

async function cadastrarUsuario(dados, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    console.log('Dados enviados:', dados); // Adicione este log para inspecionar os dados
    const response = await usuariosAPI.post("", dados, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
}

async function atualizarUsuario(dados, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    const response = await usuariosAPI.put("", dados, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return response.data;
}

async function deletarUsuario(id, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }
    await usuariosAPI.delete(`/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
    });
}

async function listarUsuarios(token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await usuariosAPI.get("", {
        headers: { Authorization: `Bearer ${token}` }
    });

    return response.data.content;
}



export { getUsuarios, cadastrarUsuario, atualizarUsuario, deletarUsuario, listarUsuarios };



