import axios from 'axios';

const emprestimosAPI = axios.create({ baseURL: "http://localhost:8080/emprestimos" });


async function listarEmprestimos (token)  {
    try {
        const response = await emprestimosAPI.get(`/ativos`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return response.data.content; 
    } catch (error) {
        console.error('Erro ao listar empréstimos ativos:', error);
        throw error;
    }

}

    async function cadastrarEmprestimo (emprestimo, token)  {
        try {
            const response = await emprestimosAPI.post(``, emprestimo, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data;
        } catch (error) {
            console.error('Erro ao cadastrar empréstimo:', error);
            throw error;
        }
    }
    

    async function atualizarEmprestimo (emprestimo, token)  {
        try {
            
            const response = await emprestimosAPI.put(``, emprestimo, {
                headers: {
                Authorization: `Bearer ${token}`,
                }
            });
            
            return response.data;
        } catch (error) {
            console.error('Erro ao atualizar o empréstimo:', error);
            throw error;
        }

    }

export { listarEmprestimos, cadastrarEmprestimo, atualizarEmprestimo };
