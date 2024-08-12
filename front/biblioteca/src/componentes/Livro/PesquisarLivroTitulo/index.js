import Input from '../../Input';
import styled from 'styled-components';
import { useState } from 'react';
import { getLivros } from '../../../servicos/livros';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const PesquisaContainer = styled.section`
  background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 85px 0;
  height: 60vh;
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


const Subtitulo = styled.h3`
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 40px;
`;


function Pesquisa() {
    const [livrosPesquisados, setLivrosPesquisados] = useState([]);
    const [titulo, setTitulo] = useState('');



    const fetchLivros = async () => {
        try {
            const token = localStorage.getItem('token');
            const livros = await getLivros(titulo, token);
            setLivrosPesquisados(livros);
        } catch (error) {
            console.error('Erro ao buscar livros', error);
            alert('Erro ao buscar livros. Verifique se você está logado.');
        }
    };

    return (
        <PesquisaContainer>
            <Titulo cor="#FFF" tamanhoFonte="24px">
                Pesquisa de Livros
            </Titulo>
            <Subtitulo>Encontre o livro que deseja</Subtitulo>
            <Input
                placeholder="Digite o nome do livro"
                value={titulo}
                onChange={(evento) => setTitulo(evento.target.value)}
            />
            <BotaoContainer>
                <Botao onClick={fetchLivros}>Pesquisar</Botao>
            </BotaoContainer>

            <ResultadosContainer>
                {livrosPesquisados.map(livros => (
                    <Resultado key={livros.id}>
                        <div>
                            <strong>Titulo:</strong>
                            <p>{livros.titulo}</p>
                        </div>
                        <div>
                            <strong>Autor:</strong>
                            <p>{livros.autor}</p>
                        </div>
                        <div>
                            <strong>Isbn:</strong>
                            <p>{livros.isbn}</p>
                        </div>
                        <div>
                            <strong>Data de publicacao:</strong>
                            <p>{livros.dataPublicacao}</p>
                        </div>
                        <div>
                            <strong>Categoria:</strong>
                            <p>{livros.categoria}</p>
                        </div>
                    </Resultado>
                ))
                }
            </ResultadosContainer>
        </PesquisaContainer>

    );
}

export default Pesquisa;
