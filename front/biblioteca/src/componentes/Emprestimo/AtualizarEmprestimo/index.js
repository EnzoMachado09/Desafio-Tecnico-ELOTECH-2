// src/componentes/Emprestimos/AtualizarEmprestimos/index.js

import React, { useState } from 'react';
import styled from 'styled-components';
import { atualizarEmprestimo } from '../../../servicos/emprestimos';
import Input from '../../Input';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const AtualizacaoContainer = styled.section`
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

function AtualizarEmprestimo() {
    const [emprestimoAtualizado, setEmprestimoAtualizado] = useState({
        id: '',
        dataDevolucao: ''
    });

    const atualizarDadosEmprestimo = async () => {
        try {
            const token = localStorage.getItem('token');
            const emprestimo = await atualizarEmprestimo(emprestimoAtualizado, token);
            console.log('Empréstimo atualizado com sucesso:', emprestimo);

            alert('Empréstimo atualizado com sucesso!');

            // Limpa o formulário após a atualização
            setEmprestimoAtualizado({
                id: '',
                dataDevolucao: ''
            });
        } catch (error) {
            console.error("Erro ao atualizar empréstimo", error);
            alert('Erro ao atualizar empréstimo. Verifique os dados e tente novamente.');
        }
    };

    return (
        <AtualizacaoContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Atualização de Empréstimos
            </Titulo>

            <Input
                placeholder="ID do Empréstimo"
                value={emprestimoAtualizado.id}
                onChange={(e) => setEmprestimoAtualizado({ ...emprestimoAtualizado, id: e.target.value })}
            />
            <Input
                placeholder="Data de Devolução"
                value={emprestimoAtualizado.dataDevolucao}
                onChange={(e) => setEmprestimoAtualizado({ ...emprestimoAtualizado, dataDevolucao: e.target.value })}
            />
            <Botao onClick={atualizarDadosEmprestimo}>Atualizar</Botao>
        </AtualizacaoContainer>
    );
}

export default AtualizarEmprestimo;
