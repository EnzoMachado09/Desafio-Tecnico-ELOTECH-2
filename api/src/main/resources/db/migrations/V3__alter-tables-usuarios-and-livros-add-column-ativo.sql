--adicionei a coluna ativo nas tabelas usuarios e livros e setei o valor default para 1
ALTER TABLE usuarios ADD ativo TINYINT NOT NULL DEFAULT 1;
ALTER TABLE livros ADD ativo TINYINT NOT NULL DEFAULT 1;
--atualizei os registros existentes para setar o valor 1 na coluna ativo
UPDATE usuarios SET ativo = 1 WHERE ativo IS NULL;
UPDATE livros SET ativo = 1 WHERE ativo IS NULL;

