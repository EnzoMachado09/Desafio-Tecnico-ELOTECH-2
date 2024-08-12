import Logo from '../Logo';
import OpcoesHeader from '../Menu';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const HeaderContainer = styled.header`
background-color: rgb(240, 248, 255);
    display: flex;
    justify-content: center;
    `

function Header() {
    return (
        <HeaderContainer>
            <Link to="/">
                <Logo/>
            </Link>
            <OpcoesHeader />
        </HeaderContainer>
    )
}


export default Header;