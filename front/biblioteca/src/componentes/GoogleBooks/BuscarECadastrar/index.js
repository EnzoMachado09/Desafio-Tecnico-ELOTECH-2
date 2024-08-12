import React, { useState } from 'react';
import styled from 'styled-components';
import Input from '../../Input';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';
import { getLivrosGoogle } from '../../../servicos/googleBooks';
import { cadastrarLivro } from '../../../servicos/livros';

const BuscarContainer = styled.section`
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
  border-radius: 18px;
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

const BotaoContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
`;

function BuscarECadastrar() {
    const [titulo, setTitulo] = useState('');
    const [livros, setLivros] = useState([]);
    const [livroSelecionado, setLivroSelecionado] = useState(null);

    const Buscar = async () => {
        try {
            const token = localStorage.getItem('token');
            const resultados = await getLivrosGoogle(titulo, token);
            setLivros(resultados);
            console.log('Livros encontrados:', resultados);
        } catch (error) {
            console.error('Erro ao buscar livros:', error);
            alert('Erro ao buscar livros. Tente novamente.');
        }
    };


    const Cadastrar = async () => {
        try {
            const token = localStorage.getItem('token');
            if (livroSelecionado) {
                console.log('Dados para cadastrar:', livroSelecionado);
                await cadastrarLivro(livroSelecionado, token);
                alert('Livro cadastrado com sucesso!');
                setLivroSelecionado(null);
            }
        } catch (error) {
            console.error('Erro ao cadastrar livro:', error);
            alert('Erro ao cadastrar livro. Tente novamente.');
        }
    };

    return (
        <BuscarContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Buscar e Cadastrar Livro
            </Titulo>

            <Input
                placeholder="Título do Livro"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
            />
            <BotaoContainer>
                <Botao onClick={Buscar}>Buscar</Botao>
            </BotaoContainer>

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
                        <BotaoContainer>
                            <Botao
                                cor="blue"
                                onClick={() => setLivroSelecionado(livro)}
                            >
                                Selecionar
                            </Botao>
                        </BotaoContainer>

                        {livroSelecionado && (
                            <div>
                                <Titulo
                                    cor="#333" tamanhoFonte="30px"
                                >Confirmar Cadastro</Titulo>
                                <Botao onClick={Cadastrar}>Cadastrar Livro Selecionado</Botao>
                            </div>
                        )}
                    </Resultado>
                ))}
            </ResultadosContainer>
        </BuscarContainer>
    );

}
export default BuscarECadastrar;
