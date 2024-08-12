# Biblioteca Gestão API
Este projeto é uma API para gerenciamento de biblioteca, incluindo funcionalidades para recomendação de livros, gerenciamento de usuários e empréstimos.

# Índice
* Pré-requisitos
* Configuração do Banco de Dados
* Execução da Aplicação
* Testes
* Rotas da API
* Pré-requisitos
* 
Antes de começar, certifique-se de ter o seguinte instalado:

Java 17 ou superior
Maven
Um banco de dados relacional (por exemplo, MySQL, PostgreSQL)
Configuração do Banco de Dados
Criação do Banco de Dados

Primeiro, crie um banco de dados no seu sistema de gerenciamento de banco de dados. Por exemplo, para MySQL, você pode executar o seguinte comando:
CREATE DATABASE biblioteca;

# Configuração das Credenciais do Banco de Dados

No arquivo src/main/resources/application.properties, configure as credenciais do banco de dados. Atualize os seguintes parâmetros conforme necessário:

# URL do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_gestao

# Nome de usuário do banco de dados
spring.datasource.username=seu_usuario

# Senha do banco de dados
spring.datasource.password=sua_senha

# Driver do banco de dados
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configurações adicionais (opcional)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

Substitua seu_usuario e sua_senha pelos valores apropriados para o seu banco de dados.



# Dependências

Verifique se você possui a dependência do driver JDBC do seu banco de dados no pom.xml. Por exemplo, para MySQL:

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.29</version>
</dependency>


# Execução da Aplicação
Compilar o Projeto

Navegue até o diretório do projeto e execute o comando Maven para compilar o projeto:


# Executar a Aplicação

Após a compilação bem-sucedida, você pode iniciar a aplicação usando o comando:

mvn spring-boot:run

A aplicação será iniciada e estará disponível em http://localhost:8080.


# Testes
Para executar os testes, use o seguinte comando Maven:
mvn test


Certifique-se de que o banco de dados esteja configurado corretamente para que os testes possam ser executados com sucesso.

# Rotas da API
Aqui estão algumas rotas importantes da API:

Recomendar Livros: GET /recomendacoes/{usuarioId}
Descrição: Recomenda livros com base nas categorias dos livros emprestados por um usuário.
Parâmetros:
usuarioId (Path Variable): ID do usuário para o qual os livros serão recomendados.

Exemplo de Requisição
Para recomendar livros para o usuário com ID 1:
curl -X GET "http://localhost:8080/recomendacoes/1"

Resposta
A resposta será uma lista de livros recomendados em formato JSON ou um status 204 No Content se não houver livros recomendados.

