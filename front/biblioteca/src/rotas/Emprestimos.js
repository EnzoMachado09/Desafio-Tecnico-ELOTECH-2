import styled from 'styled-components';
import ListarEmprestimos from '../componentes/Emprestimo/ListarEmprestimos';
import CadastrarEmprestimo from '../componentes/Emprestimo/CadastrarEmprestimo';
import AtualizarEmprestimo from '../componentes/Emprestimo/AtualizarEmprestimo';


const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function Emprestimos() {
    return (
        <AppContainer >
            <CadastrarEmprestimo />
            <ListarEmprestimos />
            <AtualizarEmprestimo />
        </AppContainer>
    );
}

export default Emprestimos;