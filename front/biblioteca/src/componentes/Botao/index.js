import styled from 'styled-components';

export const Botao = styled.button`
    background-color: ${props => props.cor || '#EB9B00'}; 
    color: #FFF;
    padding: 10px 0px;
    font-size: 16px;
    border: none;
    font-weight: 900;
    display: block;
    text-align: center;
    width: 150px;
    border-radius: 10px;
    &:hover {
        cursor: pointer;
    }
        
`;

