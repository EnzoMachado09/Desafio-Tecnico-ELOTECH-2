import styled from 'styled-components';
import PesquisaRecomendacoes from '../componentes/Recomendacao';   


const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function Recomendacoes() {
    return (
        <AppContainer >
            <PesquisaRecomendacoes/>
        </AppContainer>
    );
}

export default Recomendacoes;