import styled from 'styled-components';
import FazerLogin from '../componentes/FazerLogin';

const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function Login() {
    return (
        <AppContainer>
            <FazerLogin />
        </AppContainer>
    );
}

export default Login;
