import styled from 'styled-components';
import PesquisaLivro from '../componentes/Livro/PesquisarLivroTitulo';
import CadastroLivro from '../componentes/Livro/CadastrarLivro';
import ListarLivros from '../componentes/Livro/ListarLivro';
import AtualizarLivro from '../componentes/Livro/AtualizarLivro';

const AppContainer = styled.div`
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(90deg, #004e8a 35%, #326589);
`;

function Livros() {
    return (
        <AppContainer >
            <PesquisaLivro />
            <CadastroLivro />
            <ListarLivros />
            <AtualizarLivro />
        </AppContainer>
    );
}

export default Livros;
