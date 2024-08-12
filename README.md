# Biblioteca Gestão API - Frontend
Este projeto é uma API para gerenciamento de biblioteca, incluindo funcionalidades para recomendação de livros, gerenciamento de usuários e empréstimos.

## Índice
* Pré-requisitos
* Configuração do Banco de Dados
* Execução da Aplicação
* Testes
* Rotas da API
* Pré-requisitos
  
Antes de começar, certifique-se de ter o seguinte instalado:

Java 17 ou superior
Maven
Um banco de dados relacional (por exemplo, MySQL, PostgreSQL)
Configuração do Banco de Dados
Criação do Banco de Dados

Primeiro, crie um banco de dados no seu sistema de gerenciamento de banco de dados. Por exemplo, para MySQL, você pode executar o seguinte comando:
CREATE DATABASE biblioteca;

## Configuração das Credenciais do Banco de Dados

No arquivo src/main/resources/application.properties, configure as credenciais do banco de dados. Atualize os seguintes parâmetros conforme necessário:

### URL do banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_gestao

### Nome de usuário do banco de dados
spring.datasource.username=seu_usuario

### Senha do banco de dados
spring.datasource.password=sua_senha

### Driver do banco de dados
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### Configurações adicionais (opcional)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## Validação

A API é feita sobre uma verificação stateless, e utiliza tokens JWT para validar o usuario para as requisições.
Para poder se validar, basta criar um usuario valido diretamente no banco de dados, passando o usuario e a senha e depois usar esses dados no metodo validação,
que seu token sera gerado e automaticamente passado junto no corpo das suas requisições
obs: O token tem validação de 3 horas de uso, depois tera que realizar o login novamente

Substitua seu_usuario e sua_senha pelos valores apropriados para o seu banco de dados.



## Dependências

Verifique se você possui a dependência do driver JDBC do seu banco de dados no pom.xml. Por exemplo, para MySQL:

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.29</version>
</dependency>


## Execução da Aplicação
Compilar o Projeto

Navegue até o diretório do projeto e execute o comando Maven para compilar o projeto:


## Executar a Aplicação

Após a compilação bem-sucedida, você pode iniciar a aplicação usando o comando:

mvn spring-boot:run

A aplicação será iniciada e estará disponível em http://localhost:8080.


## Testes
Para executar os testes, use o seguinte comando Maven:
mvn test


Certifique-se de que o banco de dados esteja configurado corretamente para que os testes possam ser executados com sucesso.

## Rotas da API
Aqui estão algumas rotas importantes da API:

Recomendar Livros: GET /recomendacoes/{usuarioId}
Descrição: Recomenda livros com base nas categorias dos livros emprestados por um usuário.
Parâmetros:
usuarioId (Path Variable): ID do usuário para o qual os livros serão recomendados.

Exemplo de Requisição
Para recomendar livros para o usuário com ID 1:
curl -X GET "http://localhost:8080/recomendacoes/1"



# Biblioteca Gestão - Frontend
Este projeto é o frontend da aplicação de gerenciamento de biblioteca, permitindo interações com o sistema de recomendação de livros, gerenciamento de usuários e empréstimos.

## Índice
* Pré-requisitos
* Configuração do Projeto
* Execução da Aplicação
* Testes
* Estrutura do Projeto
* Pré-requisitos

## Antes de começar, certifique-se de ter o seguinte instalado:

* Node.js (v14 ou superior)
* npm (v6 ou superior)


## Configuração do Projeto
Clonar o Repositório:

## Instalar Dependências:

Instale as dependências necessárias usando o npm:
npm install

## Iniciar a Aplicação:

Após instalar as dependências, inicie o servidor de desenvolvimento com:
npm start

A aplicação será iniciada e estará disponível em http://localhost:3000.

## Configuração Adicional:

Certifique-se de que o backend da aplicação está em execução e configurado corretamente. O frontend está preparado para interagir com as APIs disponibilizadas pelo backend.

## Estrutura do Projeto
Aqui está uma visão geral da estrutura do projeto:

src/componentes: Contém todos os componentes React utilizados na aplicação.
src/servicos: Contém os serviços responsáveis por fazer chamadas à API backend.
src/componentes/GoogleBooks/BuscarECadastrar: Componente para buscar livros na API do Google Books e cadastrá-los na base de dados.
src/rotas: Contem todas as rotas para acessar as paginas, que é feita pelo menu no header



espero que gostem :)


