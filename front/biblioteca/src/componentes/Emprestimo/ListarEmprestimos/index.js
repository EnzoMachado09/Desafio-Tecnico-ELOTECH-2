import React, { useState } from 'react';
import styled from 'styled-components';
import { listarEmprestimos, } from '../../../servicos/emprestimos'; // Alterado para o serviço de empréstimos
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

const ResultadosUsuario = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background-color: rgb(220, 220, 230);
  border-radius: 30px;
  color: #333;
  width: 400px;
  

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

const Resultado = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background-color: rgb(240, 248, 255);
  border-radius: 30px;
  color: #333;
  width: 400px;
  height: 30%;

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

function ListarEmprestimos() {
    const [emprestimos, setEmprestimos] = useState([]);
    const [mostrarEmprestimos, setMostrarEmprestimos] = useState(false);

    const fetchEmprestimos = async () => {
        if (!mostrarEmprestimos) {
            try {
                const token = localStorage.getItem('token');
                if (token) {
                    const emprestimos = await listarEmprestimos(token); // Chama o serviço para listar os empréstimos
                    setEmprestimos(emprestimos);
                }
            } catch (error) {
                console.error('Erro ao buscar empréstimos', error);
                alert('Erro ao buscar empréstimos. Verifique se você está logado.');
            }
        }
        setMostrarEmprestimos(!mostrarEmprestimos);
    };


    return (
        <PesquisaContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Lista de Empréstimos
            </Titulo>
            <BotaoContainer>
                <Botao onClick={fetchEmprestimos} >
                    {mostrarEmprestimos ? 'Esconder' : 'Listar Empréstimos'}
                </Botao>
            </BotaoContainer>
            {mostrarEmprestimos && (
                <ResultadosContainer>
                    {emprestimos.map(emprestimo => (
                        <Resultado key={emprestimo.id}>
                            <ResultadosUsuario>
                                <div>
                                    <strong>Usuario Nome:</strong>
                                    <p>{emprestimo.usuario.nome}</p>
                                </div>
                                <div>
                                    <strong>Usuario Email:</strong>
                                    <p>{emprestimo.usuario.email}</p>
                                </div>
                                <div>
                                    <strong>Usuario Data de Cadastro:</strong>
                                    <p>{emprestimo.usuario.data_cadastro}</p>
                                </div>
                                <div>
                                    <strong>Usuario Telefone:</strong>
                                    <p>{emprestimo.usuario.telefone}</p>
                                </div>
                                <div>
                                    <strong>Usuario Id:</strong>
                                    <p>{emprestimo.usuario.id}</p>
                                </div>
                            </ResultadosUsuario>
                            <div>
                                <strong>Titulo do Livro:</strong>
                                <p>{emprestimo.livro.titulo}</p>
                            </div>
                            <div>
                                <strong>Autor do Livro:</strong>
                                <p>{emprestimo.livro.autor}</p>
                            </div>
                            <div>
                                <strong>Isbn do Livro:</strong>
                                <p>{emprestimo.livro.isbn}</p>
                            </div>
                            <div>
                                <strong>Data de publicacao do Livro:</strong>
                                <p>{emprestimo.livro.dataPublicacao}</p>
                            </div>
                            <div>
                                <strong>Categoria do Livro:</strong>
                                <p>{emprestimo.livro.categoria}</p>
                            </div>
                            <div>
                                <strong>Id do Livro:</strong>
                                <p>{emprestimo.livro.id}</p>
                            </div>
                            <ResultadosUsuario>
                                <div>
                                    <strong>Data de Empréstimo:</strong>
                                    <p>{emprestimo.dataEmprestimo}</p>
                                </div>
                                <div>
                                    <strong>Data de Devolução:</strong>
                                    <p>{emprestimo.dataDevolucao || "Não devolvido"}</p>
                                </div>
                                <div>
                                    <strong>Emprestimo Status:</strong>
                                    <p>{emprestimo.status}</p>
                                </div>
                                <div>
                                    <strong>Id do Emprestimo:</strong>
                                    <p>{emprestimo.id}</p>
                                </div>
                            </ResultadosUsuario>
                        </Resultado>
                    ))}
                </ResultadosContainer>
            )
            }
        </PesquisaContainer >
    );
}

export default ListarEmprestimos;
