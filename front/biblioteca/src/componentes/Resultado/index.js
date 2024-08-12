import styled from "styled-components"

export const Resultado = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
margin-bottom: 20px;
padding: 20px;
background-color: #f5f5f5;
border-radius: 8px;
color: #333;
width: 300px;

div {
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;

  p {
    margin: 0;
    text-align: center;
  }

  strong {
    margin-right: 5px;
    text-align: center;
  }
}

&:hover {
  border: 1px solid #004e8a;
}
`