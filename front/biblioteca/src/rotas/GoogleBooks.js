import styled from 'styled-components';
import BuscarECadastrar from '../componentes/GoogleBooks/BuscarECadastrar';


const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function GoogleBooks() {
    return (
        <AppContainer >
            <BuscarECadastrar />
        </AppContainer>
    );
}

export default GoogleBooks;