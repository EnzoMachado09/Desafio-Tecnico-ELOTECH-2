import React, { useState } from 'react';
import styled from 'styled-components';
import { atualizarLivro } from '../../../servicos/livros';
import Input from '../../Input';
import { Titulo } from '../../Titulo';
import { Botao } from '../../Botao';

const AtualizacaoContainer = styled.section`
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

function AtualizarLivro() {
    const [livroAtualizado, setLivroAtualizado] = useState({
        id: '',
        titulo: '',
        autor: '',
        isbn: '',
        dataPublicacao: '',
        categoria: ''
    });

    const atualizarDadosLivro = async () => {
        try {
            const token = localStorage.getItem('token');
            const livro = await atualizarLivro(livroAtualizado, token);
            console.log('Livro atualizado com sucesso:', livro);

            alert('Livro atualizado com sucesso!');

            // Limpa o formulário após a atualização
            setLivroAtualizado({ id: '', titulo: '', autor: '', isbn: '', dataPublicacao: '', categoria: '' });
        } catch (error) {
            console.error("Erro ao atualizar livro", error);
            alert('Erro ao atualizar livro. Verifique os dados e tente novamente.');
        }
    };

    return (
        <AtualizacaoContainer>
            <Titulo cor="#FFF" tamanhoFonte="36px">
                Atualização de Livros
            </Titulo>

            <Input
                placeholder="ID do Livro"
                value={livroAtualizado.id}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, id: e.target.value })}
            />
            <Input
                placeholder="Título"
                value={livroAtualizado.titulo}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, titulo: e.target.value })}
            />
            <Input
                placeholder="Autor"
                value={livroAtualizado.autor}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, autor: e.target.value })}
            />
            <Input
                placeholder="ISBN"
                value={livroAtualizado.isbn}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, isbn: e.target.value })}
            />
            <Input
                placeholder="Data de Publicação"
                value={livroAtualizado.dataPublicacao}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, dataPublicacao: e.target.value })}
            />
            <Input
                placeholder="Categoria"
                value={livroAtualizado.categoria}
                onChange={(e) => setLivroAtualizado({ ...livroAtualizado, categoria: e.target.value })}
            />
            <Botao onClick={atualizarDadosLivro}>Atualizar</Botao>
        </AtualizacaoContainer>
    );
}

export default AtualizarLivro;
