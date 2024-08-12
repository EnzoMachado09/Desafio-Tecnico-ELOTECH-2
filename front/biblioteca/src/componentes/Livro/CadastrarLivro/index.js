import React, { useState } from 'react';
import styled from 'styled-components';
import { cadastrarLivro } from '../../../servicos/livros';
import Input from '../../Input';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const CadastroContainer = styled.section`
background-image: linear-gradient(90deg, #004e8a 35%, #326589);
  color: #FFF;
  text-align: center;
  padding: 40px 0;
  height: 60vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

function CadastroLivro() {
    const [novoLivro, setNovoLivro] = useState({
        titulo: '',
        autor: '',
        isbn: '',
        dataPublicacao: '',
        categoria: ''
    });

    const cadastroLivro = async () => {
        try {
            const token = localStorage.getItem('token');
            const livroCadastrado = await cadastrarLivro(novoLivro, token);
            console.log('Livro cadastrado com sucesso:', livroCadastrado);

            alert('Livro cadastrado com sucesso!');

            // Limpa o formulário após o cadastro
            setNovoLivro({ titulo: '', autor: '', isbn: '', dataPublicacao: '', categoria: '' });
        } catch (error) {
            console.error("Erro ao cadastrar livro", error);
            alert('Erro ao cadastrar livro. Verifique os dados e tente novamente.');
        }
    };

    return (
        <CadastroContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Cadastro de Livros
            </Titulo>

            <Input
                placeholder="Título"
                value={novoLivro.titulo}
                onChange={(e) => setNovoLivro({ ...novoLivro, titulo: e.target.value })}
            />
            <Input
                placeholder="Autor"
                value={novoLivro.autor}
                onChange={(e) => setNovoLivro({ ...novoLivro, autor: e.target.value })}
            />
            <Input
                placeholder="ISBN"
                value={novoLivro.isbn}
                onChange={(e) => setNovoLivro({ ...novoLivro, isbn: e.target.value })}
            />
            <Input
                placeholder="Data de Publicação"
                value={novoLivro.dataPublicacao}
                onChange={(e) => setNovoLivro({ ...novoLivro, dataPublicacao: e.target.value })}
            />
            <Input
                placeholder="Categoria"
                value={novoLivro.categoria}
                onChange={(e) => setNovoLivro({ ...novoLivro, categoria: e.target.value })}
            />
            <Botao onClick={cadastroLivro}>Cadastrar</Botao>
        </CadastroContainer>
    );
}

export default CadastroLivro;
