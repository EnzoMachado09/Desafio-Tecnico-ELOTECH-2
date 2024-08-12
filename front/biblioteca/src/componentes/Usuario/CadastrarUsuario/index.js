import React, { useState } from 'react';
import styled from 'styled-components';
import { cadastrarUsuario } from '../../../servicos/usuarios';
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

function CadastroUsuario() {
    const [novoUsuario, setNovoUsuario] = useState({
        nome: '',
        email: '',
        data_cadastro: '',
        telefone: ''
    });

    const cadastroUsuario = async () => {
        try {
            const token = localStorage.getItem('token');
            const usuarioCadastrado = await cadastrarUsuario(novoUsuario, token);
            console.log('Usuário cadastrado com sucesso:', usuarioCadastrado);

            alert('Usuário cadastrado com sucesso!');

            // Limpa o formulário após o cadastro
            setNovoUsuario({ nome: '', email: '', data_cadastro: '', telefone: '' });
        } catch (error) {
            console.error("Erro ao cadastrar usuário", error);
            alert('Erro ao cadastrar usuário. Verifique os dados e tente novamente.');
        }
    };

    return (
        <CadastroContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Cadastro de Usuários
            </Titulo>

            <Input
                placeholder="Nome"
                value={novoUsuario.nome}
                onChange={(e) => setNovoUsuario({ ...novoUsuario, nome: e.target.value })}
            />
            <Input
                placeholder="Email"
                value={novoUsuario.email}
                onChange={(e) => setNovoUsuario({ ...novoUsuario, email: e.target.value })}
            />
            <Input
                placeholder="Data de Cadastro "
                value={novoUsuario.data_cadastro}
                onChange={(e) => setNovoUsuario({ ...novoUsuario, data_cadastro: e.target.value })}
            />
            <Input
                placeholder="Telefone"
                value={novoUsuario.telefone}
                onChange={(e) => setNovoUsuario({ ...novoUsuario, telefone: e.target.value })}
            />
            <Botao onClick={cadastroUsuario}>Cadastrar</Botao>
        </CadastroContainer>
    );
}

export default CadastroUsuario;
