import styled from 'styled-components';
import Pesquisa from '../componentes/Usuario/PesquisarUsuarioNome'
import CadastroUsuario from '../componentes/Usuario/CadastrarUsuario'
import ListarUsuarios from '../componentes/Usuario/ListarUsuarios'
import AtualizarUsuario from '../componentes/Usuario/AtualizarUsuario'

const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function Usuarios() {
    return (
        <AppContainer >
            <Pesquisa />
            <CadastroUsuario />
            <ListarUsuarios />
            <AtualizarUsuario />
        </AppContainer>
    );
}

export default Usuarios;