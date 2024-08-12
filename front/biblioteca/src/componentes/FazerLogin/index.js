import { useState } from 'react';
import styled from 'styled-components';
import Input from '../Input';
import { validacao } from '../../servicos/validacao.js';
import { Titulo } from '../Titulo';
import { Botao } from '../Botao/index.js';

const LoginContainer = styled.section`
  background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 85px 0;
  height: 270px;
  width: 100%;
`;

const Subtitulo = styled.h3`
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 40px;
`;

const BotaoContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px; /* Espaço opcional entre o botão e o input */
`;

function FazerLogin() {
    const [username, setUsername] = useState('');
    const [senha, setSenha] = useState('');

    const handleLogin = async () => {
        try {
            const token = await validacao(username, senha);
            localStorage.setItem('token', token);
            alert('Login realizado com sucesso!');
            // Aqui você pode redirecionar o usuário para a página principal
        } catch (error) {
            console.error('Erro ao realizar login', error);
            alert('Erro ao realizar login. Verifique suas credenciais.');
        }
    };

    return (
        <LoginContainer>
            <Titulo
                cor="#FFF"
                tamanhoFonte="24px"
            >Login</Titulo>
            <Subtitulo>Entre com suas credenciais</Subtitulo>
            <Input
                placeholder="Digite seu username"
                value={username}
                onChange={(evento) => setUsername(evento.target.value)}
            />
            <Input
                placeholder="Digite sua senha"
                type="password"
                value={senha}
                onChange={(evento) => setSenha(evento.target.value)}
            />
            <BotaoContainer>
                <Botao onClick={handleLogin}>Entrar</Botao>
            </BotaoContainer>
        </LoginContainer>
    );
}

export default FazerLogin;
