import React from 'react';
import styled from 'styled-components';

const AppContainer = styled.div`
background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 85px 0;
  width: 100vw;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  
`;

const TituloPrincipal = styled.h1`
  font-size: 48px;
  margin-bottom: 20px;
  color: #FFF;
`;

const Subtitulo = styled.h2`
  font-size: 24px;
  margin-bottom: 40px;
  color: #f0f0f0;
`;

const Lembrete = styled.p`
  font-size: 18px;
  color: #e0e0e0;
  background-color: rgba(0, 0, 0, 0.3);
  padding: 10px 20px;
  border-radius: 10px;
`;


function Home() {

    return (
        <AppContainer>
            <TituloPrincipal>Bem-vindo ao Gerenciador de Biblioteca</TituloPrincipal>
            <Subtitulo>Fique à vontade para navegar pela aplicação</Subtitulo>
            <Lembrete>Lembre-se: O formato da data é "aaaa-mm-dd"</Lembrete>
        </AppContainer>
    );
}

export default Home;
