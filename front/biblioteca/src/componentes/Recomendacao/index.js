import Input from '../Input';
import styled from 'styled-components';
import { useState } from 'react';
import { getRecomendacoesPorUsuario } from '../../servicos/recomendecoes';
import { Titulo } from '../Titulo';
import { Botao } from '../Botao';

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

const Subtitulo = styled.h3`
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 40px;
`;

function PesquisaRecomendacoes() {
    const [recomendacoes, setRecomendacoes] = useState([]);
    const [idUsuario, setIdUsuario] = useState('');

    const fetchRecomendacoes = async () => {
        try {
            const token = localStorage.getItem('token');
            const livrosRecomendados = await getRecomendacoesPorUsuario(idUsuario, token);
            setRecomendacoes(livrosRecomendados);
            console.log(livrosRecomendados);
        } catch (error) {
            console.error('Erro ao buscar recomendações', error);
            alert('Erro ao buscar recomendações. Verifique se você está logado.');
        }
    };

    return (
        <PesquisaContainer>
            <Titulo cor="#FFF" tamanhoFonte="24px">
                Pesquisa de Recomendações
            </Titulo>
            <Subtitulo>Encontre livros recomendados para o usuário</Subtitulo>
            <Input
                placeholder="Digite o ID do usuário"
                value={idUsuario}
                onChange={(evento) => setIdUsuario(evento.target.value)}
            />
            <BotaoContainer>
                <Botao onClick={fetchRecomendacoes}>Pesquisar</Botao>
            </BotaoContainer>

            <ResultadosContainer>
                {Array.isArray(recomendacoes) && recomendacoes.length > 0 &&
                    recomendacoes.map(livro => (
                        <Resultado key={livro.id}>
                            <div>
                                <strong>Título:</strong>
                                <p>{livro.titulo}</p>
                            </div>
                            <div>
                                <strong>Autor:</strong>
                                <p>{livro.autor}</p>
                            </div>
                            <div>
                                <strong>Data de Publicação:</strong>
                                <p>{livro.data_publicacao}</p>
                            </div>
                            <div>
                                <strong>Categoria:</strong>
                                <p>{livro.categoria}</p>
                            </div>
                        </Resultado>
                    ))
                }
            </ResultadosContainer>
        </PesquisaContainer>
    );
}

export default PesquisaRecomendacoes;
