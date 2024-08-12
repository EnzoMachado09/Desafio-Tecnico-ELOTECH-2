import Input from '../../Input';
import styled from 'styled-components';
import { useState } from 'react';
import { getUsuarios } from '../../../servicos/usuarios';
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
  const [usuariosPesquisados, setUsuariosPesquisados] = useState([]);
  const [nome, setNome] = useState('');



  const fetchUsuarios = async () => {
    try {
      const token = localStorage.getItem('token');
      const usuarios = await getUsuarios(nome, token);
      setUsuariosPesquisados(usuarios);
    } catch (error) {
      console.error('Erro ao buscar usuários', error);
      alert('Erro ao buscar usuários. Verifique se você está logado.');
    }
  };

  return (
    <PesquisaContainer>
      <Titulo cor="#FFF" tamanhoFonte="24px">
        Pesquisa de Usuários
      </Titulo>
      <Subtitulo>Encontre o usuário que deseja</Subtitulo>
      <Input
        placeholder="Digite o nome do usuário"
        value={nome}
        onChange={(evento) => setNome(evento.target.value)}
      />
      <BotaoContainer>
        <Botao onClick={fetchUsuarios}>Pesquisar</Botao>
      </BotaoContainer>

      <ResultadosContainer>
        {usuariosPesquisados.map(usuario => (
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
          </Resultado>
        ))
        }
      </ResultadosContainer>
    </PesquisaContainer>

  );
}

export default Pesquisa;
