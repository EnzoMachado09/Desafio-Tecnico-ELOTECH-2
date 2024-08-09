-- Criação da tabela emprestimos
CREATE TABLE emprestimos (
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    usuario_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_livro FOREIGN KEY (livro_id) REFERENCES livros(id)
);

--índices para melhorar a performance das consultas
CREATE INDEX idx_usuario_id ON emprestimos(usuario_id);
CREATE INDEX idx_livro_id ON emprestimos(livro_id);
