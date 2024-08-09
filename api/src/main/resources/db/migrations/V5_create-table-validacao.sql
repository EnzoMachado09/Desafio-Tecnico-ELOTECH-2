-- Criação da tabela validacao
CREATE TABLE livros (
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    login VARCHAR(150) NOT NULL,
    senha VARCHAR(255) NOT NULL,
)