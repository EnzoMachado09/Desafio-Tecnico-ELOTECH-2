import React, { useState } from 'react';
import styled from 'styled-components';
import { atualizarUsuario } from '../../../servicos/usuarios';
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

function AtualizarUsuario() {
    const [usuarioAtualizado, setUsuarioAtualizado] = useState({
        id: '',
        nome: '',
        email: '',
        data_cadastro: '',
        telefone: ''
    });

    const atualizarDadosUsuario = async () => {
        try {
            const token = localStorage.getItem('token');
            const usuario = await atualizarUsuario(usuarioAtualizado, token);
            console.log('Usuário atualizado com sucesso:', usuario);

            alert('Usuário atualizado com sucesso!');

            // Limpa o formulário após a atualização
            setUsuarioAtualizado({ id: '', nome: '', email: '', data_cadastro: '', telefone: '' });
        } catch (error) {
            console.error("Erro ao atualizar usuário", error);
            alert('Erro ao atualizar usuário. Verifique os dados e tente novamente.');
        }
    };

    return (
        <AtualizacaoContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Atualização de Usuários
            </Titulo>

            <Input
                placeholder="ID do Usuário"
                value={usuarioAtualizado.id}
                onChange={(e) => setUsuarioAtualizado({ ...usuarioAtualizado, id: e.target.value })}
            />
            <Input
                placeholder="Nome"
                value={usuarioAtualizado.nome}
                onChange={(e) => setUsuarioAtualizado({ ...usuarioAtualizado, nome: e.target.value })}
            />
            <Input
                placeholder="Email"
                value={usuarioAtualizado.email}
                onChange={(e) => setUsuarioAtualizado({ ...usuarioAtualizado, email: e.target.value })}
            />
            <Input
                placeholder="Data de Cadastro"
                value={usuarioAtualizado.data_cadastro}
                onChange={(e) => setUsuarioAtualizado({ ...usuarioAtualizado, data_cadastro: e.target.value })}
            />
            <Input
                placeholder="Telefone"
                value={usuarioAtualizado.telefone}
                onChange={(e) => setUsuarioAtualizado({ ...usuarioAtualizado, telefone: e.target.value })}
            />
            <Botao onClick={atualizarDadosUsuario}>Atualizar</Botao>
        </AtualizacaoContainer>
    );
}

export default AtualizarUsuario;
