import logo from '../../Imagens/Logo.png';
import styled from 'styled-components';

const LogoContainer = styled.div`
    display: flex;
    font-size: 23px;
`

const LogoImg = styled.img`
margin-right: 10px;
`

function Logo() {
    return (
        <LogoContainer>
            <LogoImg
                src={logo}
                alt='logo'
            ></LogoImg>
            <p><strong>Biblioteca</strong>Google</p>
        </LogoContainer>
    );
}


export default Logo;