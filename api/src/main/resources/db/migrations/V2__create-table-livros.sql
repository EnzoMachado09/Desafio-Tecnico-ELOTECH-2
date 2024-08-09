-- Criação da tabela livros
CREATE TABLE livros (
    id BIGINT NOT NULL PRIMARY KEY IDENTITY(1,1),
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    data_publicacao DATE NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL
)