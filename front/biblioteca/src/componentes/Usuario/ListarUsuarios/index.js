import React, { useState } from 'react';
import styled from 'styled-components';
import { listarUsuarios, deletarUsuario } from '../../../servicos/usuarios';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const PesquisaContainer = styled.section`
  background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 85px 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const ResultadosContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 20px;
`;

const Resultado = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background-color: rgb(240, 248, 255);
  border-radius: 8px;
  color: #333;
  width: 300px;

  div {
    margin-bottom: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;

    p {
      margin: 0;
      text-align: center;
    }

    strong {
      margin-right: 5px;
      text-align: center;
    }
  }

  &:hover {
    border: 1px solid #004e8a;
  }
`;

const BotaoContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
`;

function ListarUsuarios() {
    const [usuarios, setUsuarios] = useState([]);
    const [mostrarUsuarios, setMostrarUsuarios] = useState(false);

    const fetchUsuarios = async () => {
        if (!mostrarUsuarios) {
            try {
                const token = localStorage.getItem('token');
                if (token) {
                    const usuarios = await listarUsuarios(token);
                    setUsuarios(usuarios);
                }
            } catch (error) {
                console.error('Erro ao buscar usuários', error);
                alert('Erro ao buscar usuários. Verifique se você está logado.');
            }
        }
        setMostrarUsuarios(!mostrarUsuarios);
    };


    const Deletar = async (id) => {
        const confirm = window.confirm("Tem certeza que deseja deletar este usuário?");
        if (confirm) {
            try {
                const token = localStorage.getItem('token');
                await deletarUsuario(id, token);
                setUsuarios(usuarios.filter((usuario) => usuario.id !== id));
            } catch (error) {
                console.error("Erro ao deletar Usuario", error);
                alert('Erro ao deletar Usuario.');
            }

        }
    };



    return (
        <PesquisaContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Lista de Usuários
            </Titulo>
            <BotaoContainer>
                <Botao onClick={fetchUsuarios} >
                    {mostrarUsuarios ? 'Esconder' : 'Listar Usuários'}
                </Botao>
            </BotaoContainer>
            {mostrarUsuarios && (
                <ResultadosContainer>
                    {usuarios.map(usuario => (
                        <Resultado key={usuario.id}>
                            <div>
                                <strong>Nome:</strong>
                                <p>{usuario.nome}</p>
                            </div>
                            <div>
                                <strong>Email:</strong>
                                <p>{usuario.email}</p>
                            </div>
                            <div>
                                <strong>Data de Cadastro:</strong>
                                <p>{usuario.data_cadastro}</p>
                            </div>
                            <div>
                                <strong>Telefone:</strong>
                                <p>{usuario.telefone}</p>
                            </div>
                            <div>
                                <strong>Id:</strong>
                                <p>{usuario.id}</p>
                            </div>
                            <BotaoContainer>
                                <Botao
                                    cor='red'
                                    onClick={() => Deletar(usuario.id)}>Deletar</Botao>
                            </BotaoContainer>
                        </Resultado>
                    ))}
                </ResultadosContainer>
            )}
        </PesquisaContainer>
    );
}


export default ListarUsuarios;
