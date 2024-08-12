import axios from "axios";

const googleAPI = axios.create({ baseURL: "http://localhost:8080/googlebooks/buscar" });

async function getLivrosGoogle(titulo, token) {
    if (!token) {
        throw new Error('Usuário não está logado');
    }

    const response = await googleAPI.get(`/${titulo}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    return response.data;
}



export { getLivrosGoogle };