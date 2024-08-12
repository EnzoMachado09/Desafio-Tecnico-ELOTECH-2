import React, { useState } from 'react';
import styled from 'styled-components';
import { cadastrarEmprestimo } from '../../../servicos/emprestimos'; // Ajuste o caminho para o serviço de empréstimos
import Input from '../../Input';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const CadastroContainer = styled.section`
  background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 40px 0;
  height: 60vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

function CadastrarEmprestimo() {
    const [novoEmprestimo, setNovoEmprestimo] = useState({
        usuarioId: '',
        livroId: '',
        dataEmprestimo: ''
    });

    const cadastroEmprestimo = async () => {
        try {
            const token = localStorage.getItem('token');
            const emprestimoCadastrado = await cadastrarEmprestimo(novoEmprestimo, token);
            console.log('Empréstimo cadastrado com sucesso:', emprestimoCadastrado);

            alert('Empréstimo cadastrado com sucesso!');

            // Limpa o formulário após o cadastro
            setNovoEmprestimo({ usuarioId: '', livroId: '', dataEmprestimo: '' });
        } catch (error) {
            console.error("Erro ao cadastrar empréstimo", error);
            alert('Erro ao cadastrar empréstimo. Verifique os dados e tente novamente.');
        }
    };

    return (
        <CadastroContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Cadastro de Empréstimos
            </Titulo>

            <Input
                placeholder="ID do Usuário"
                value={novoEmprestimo.usuarioId}
                onChange={(e) => setNovoEmprestimo({ ...novoEmprestimo, usuarioId: e.target.value })}
            />
            <Input
                placeholder="ID do Livro"
                value={novoEmprestimo.livroId}
                onChange={(e) => setNovoEmprestimo({ ...novoEmprestimo, livroId: e.target.value })}
            />
            <Input
                placeholder="Data de Empréstimo"
                value={novoEmprestimo.dataEmprestimo}
                onChange={(e) => setNovoEmprestimo({ ...novoEmprestimo, dataEmprestimo: e.target.value })}
            />
            <Botao onClick={cadastroEmprestimo}>Cadastrar</Botao>
        </CadastroContainer>
    );
}

export default CadastrarEmprestimo;
