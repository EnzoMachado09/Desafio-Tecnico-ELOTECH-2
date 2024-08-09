-- Criação da tabela de usuários
CREATE TABLE usuarios (
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    data_cadastro DATE NOT NULL,
    telefone VARCHAR(20) NOT NULL
);
