import React, { useState } from 'react';
import styled from 'styled-components';
import { listarLivros, deletarLivro } from '../../../servicos/livros';
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

function ListarLivros() {
    const [livros, setLivros] = useState([]);
    const [mostrarLivros, setMostrarLivros] = useState(false);

    const MostrarLivros = async () => {
        if (!mostrarLivros) {
            try {
                const token = localStorage.getItem('token');
                const listaLivros = await listarLivros(token);
                setLivros(listaLivros);
            } catch (error) {
                console.error("Erro ao listar livros", error);
                alert('Erro ao listar livros.');
            }
        }
        setMostrarLivros(!mostrarLivros);
    };

    const Deletar = async (id) => {
        const confirm = window.confirm("Tem certeza que deseja deletar este usuário?");
        if (confirm) {
            try {
                const token = localStorage.getItem('token');
                await deletarLivro(id, token);
                setLivros(livros.filter((livro) => livro.id !== id));
            } catch (error) {
                console.error("Erro ao deletar livro", error);
                alert('Erro ao deletar livro.');
            }
        }
    };

    return (
        <PesquisaContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Lista de Livros
            </Titulo>
            <Botao onClick={MostrarLivros}>
                {mostrarLivros ? "Ocultar Livros" : "Mostrar Livros"}
            </Botao>
            {mostrarLivros && (
                <ResultadosContainer>
                    {livros.map(livro => (
                        <Resultado key={livro.id}>
                            <div>
                                <strong>Titulo:</strong>
                                <p>{livro.titulo}</p>
                            </div>
                            <div>
                                <strong>Autor:</strong>
                                <p>{livro.autor}</p>
                            </div>
                            <div>
                                <strong>Isbn:</strong>
                                <p>{livro.isbn}</p>
                            </div>
                            <div>
                                <strong>Data de publicacao:</strong>
                                <p>{livro.dataPublicacao}</p>
                            </div>
                            <div>
                                <strong>Categoria:</strong>
                                <p>{livro.categoria}</p>
                            </div>
                            <div>
                                <strong>Id:</strong>
                                <p>{livro.id}</p>
                            </div>
                            <BotaoContainer>
                                <Botao
                                    cor='red'
                                    onClick={() => Deletar(livro.id)}>Deletar</Botao>
                            </BotaoContainer>
                        </Resultado>
                    ))}
                </ResultadosContainer>
            )}
        </PesquisaContainer>
    );
}

export default ListarLivros;
