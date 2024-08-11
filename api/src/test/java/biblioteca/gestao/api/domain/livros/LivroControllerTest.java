package biblioteca.gestao.api.domain.livros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class LivroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosLivros> dadosLivrosJson;

    @Autowired
    private JacksonTester<DadosDetalhadosLivros> dadosDetalhadosLivrosJson;

    @Autowired
    private JacksonTester<DadosAtualizarLivros> dadosAtualizarLivrosJson;

    @MockBean
    private LivroRepository repository;

    @Test
    @DisplayName("Deve devolver o codigo 400 ao tentar cadastrar um Livro com dados invalidos")
    @WithMockUser // Simula um livro autenticado
    void cadastrarLivroCase1() throws Exception {
        //Act
        var response = mvc.perform(post("/livros")) // Envia uma requisição POST para /livros usando o MockMvc
                .andReturn().getResponse(); // Captura a resposta da requisição

        //Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // Verifica se o status da resposta
                                                                                    // é 400
    }

    @Test
    @DisplayName("Deve devolver o codigo 200 ao tentar cadastrar um Livro com dados validos")
    @WithMockUser // Simula um livro autenticado
    void cadastrarLivroCase2() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data atual

        // Act
        var response = mvc .perform(post("/livros")
                                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como JSON
                                .content(dadosLivrosJson.write( // Escreve os dados do livro
                                        new DadosLivros("Livro Teste", "autor teste", "123", data, "Categoria teste"))
                                        .getJson())) // Converte os dados do livro para JSON
                                        .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()); // Verifica se o status da resposta é 200

        var jsonEsperado = dadosDetalhadosLivrosJson.write( // Escreve os dados do livro esperados
                new DadosDetalhadosLivros(null, "Livro Teste", "autor teste", "123", data, "Categoria teste"))
                .getJson(); // Converte os dados do livro esperados para JSON

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se a resposta contém os dados esperados
    }

    @Test
    @DisplayName("Deve devolver o código 400 ao tentar atualizar um livro com dados inválidos")
    @WithMockUser // Simula um livro autenticado
    void atualizarLivroCase1() throws Exception {
        // Act
        var response = mvc.perform(put("/livros")) // Envia uma requisição PUT para /Livros usando o MockMvc
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // Verifica se o status da resposta é 400
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao tentar atualizar um livro com dados válidos")
    @WithMockUser // Simula um livro autenticado
    void atualizarLivroCase2() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data atual

        // Simulação de livro já existente no banco de dados
        Livro livro = new Livro(1L, "Livro Teste", "autor teste", "123", data, true, "categoria teste");
        when(repository.getReferenceById(livro.getId())).thenReturn(livro); // Simula o retorno do livro pelo id

        // Dados do livro atualizado
        var dadosAtualizar = new DadosAtualizarLivros(1L, "Livro Atualizado", "autor atualizado", "465", data,
                "categoria atualizada");

        // Act
        var response = mvc .perform(put("/livros")
                                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como JSON
                                .content(dadosAtualizarLivrosJson.write(dadosAtualizar).getJson())) // Converte os dados para JSON
                                .andReturn().getResponse(); // Captura a resposta da requisição
        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        var jsonEsperado = dadosDetalhadosLivrosJson.write( // Escreve os dados esperados do livro atualizado
                new DadosDetalhadosLivros(1L, "Livro Atualizado", "autor atualizado", "465", data,
                        "categoria atualizada"))
                .getJson(); // Converte os dados esperados para JSON

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se a resposta contém os dados
                                                                           // esperados
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao tentar listar livros existentes")
    @WithMockUser // Simula um livro autenticado
    void listarLivrosCase1() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data atual

        // Simulação de uma lista de livros ativos
        var livro1 = new Livro(1L, "Livro Teste 1", "autor teste 1", "123", data, true, "categoria teste 1");
        var livro2 = new Livro(2L, "Livro Teste 2", "autor teste 2", "456", data, true, "categoria teste 2");
        var listaLivros = List.of(livro1, livro2);

        // Simula o retorno do repository como uma lista dentro de um Page para simular a paginação
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(new PageImpl<>(listaLivros));

        // Act
        var response = mvc.perform(get("/livros") // Envia uma requisição GET para /Livros usando o MockMvc
                .param("page", "0") // Define o parâmetro de página como 0
                .param("size", "10") // Define o parâmetro de tamanho como 10
                .param("sort", "titulo")) // Define o parâmetro de ordenação como titulo
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        // Converta a lista de livros para JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Adiciona o módulo para suportar LocalDate
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Configura para não usar timestamps

        // Converta a lista de livros para JSON
        String listaLivrosJson = mapper.writeValueAsString(listaLivros);

        // Extraia o conteúdo JSON da resposta
        String responseContent = response.getContentAsString();
        JsonNode responseJson = mapper.readTree(responseContent).get("content");

        // Converta o conteúdo extraído para JSON e compare com a lista esperada
        String contentJson = mapper.writeValueAsString(responseJson);

        assertThat(contentJson).isEqualTo(listaLivrosJson); // Verifica se o conteúdo da resposta contém os dados esperados

    }

    @Test
    @DisplayName("Deve devolver o código 204 ao tentar listar livros quando não há nenhum cadastradado")
    @WithMockUser // Simula um usuário autenticado
    void listarLivrosCase2() throws Exception {
        // Arrange
        // Cria uma lista vazia de livros
        var listaLivros = List.<Livro>of();

        // Simula o retorno do repository como uma lista vazia dentro de um Page
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(new PageImpl<Livro>(listaLivros));

        // Act
        var response = mvc.perform(get("/livros") // Envia uma requisição GET para /livros usando o MockMvc
                .param("page", "0") // Define o parâmetro de página como 0
                .param("size", "10") // Define o parâmetro de tamanho como 10
                .param("sort", "titulo")) // Define o parâmetro de ordenação como titulo
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()); // Verifica se o status da resposta é
                                                                                   // 204

        assertThat(response.getContentAsString()).isEmpty(); // Verifica se a resposta está vazia
    }


    @Test
@DisplayName("Deve devolver o código 204 ao deletar um livro existente")
@WithMockUser // Simula um usuário autenticado
void deletarLivro() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data atual
    var livro = new Livro(1L, "Livro Teste", "Autor Teste", "123456789", data, true, "teste");

    when(repository.getReferenceById(livro.getId())).thenReturn(livro); // Simula o retorno do livro pelo id
 
    // Act
    var response = mvc.perform(delete("/livros/{id}", livro.getId())) // Envia uma requisição DELETE para /livros/{id} usando o MockMvc
                      .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()); // Verifica se o status da resposta é 204
    assertThat(livro.getAtivo()).isFalse(); // Verifica se o livro foi marcado como inativo
}


@Test
@DisplayName("Deve devolver o código 200 ao buscar um livro por ID")
@WithMockUser // Simula um usuário autenticado
void buscarLivroPorId() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data atual
    var livro = new Livro(1L, "Livro Teste", "Autor Teste", "123456789", data, true, "teste");

    when(repository.findByIdAndAtivoTrue(livro.getId())).thenReturn(livro);

    // Act
    var response = mvc.perform(get("/livros/id/{id}", livro.getId()))
                      .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

    // Converta o livro para JSON
    var jsonEsperado = dadosDetalhadosLivrosJson.write(new DadosDetalhadosLivros(livro)) // Escreve os dados do livro
                                                   .getJson(); // Converte os dados do livro para JSON

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se o conteúdo da resposta contém os dados esperados
}


@Test
@DisplayName("Deve devolver o código 200 ao buscar um livro por Titulo")
@WithMockUser // Simula um usuário autenticado
void buscarLivroPorTitulo() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data atual 

        // Simulação de uma lista de usuários ativos
        var livro1 = new Livro(1L, "Livro Teste 1", "Autor Teste 1", "123456789", data, true, "teste 1");
        var livro2 = new Livro(2L, "Livro Teste 2", "Autor Teste 2", "123456789", data, true, "teste 2");
        var listalivros = List.of(livro1, livro2); 

        // Simula o retorno do repository como uma lista dentro de um Page
        when(repository.findByTituloAndAtivoTrue(eq(livro1.getTitulo()), any(Pageable.class))).thenReturn(new PageImpl<>(listalivros));

    // Act
    var response = mvc.perform(get("/livros/titulo/{titulo}", livro1.getTitulo()))
                      .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

     // Converta a lista de usuários para JSON
     ObjectMapper mapper = new ObjectMapper();
     mapper.registerModule(new JavaTimeModule()); // Adiciona o módulo para suportar LocalDate
     mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Configura para não usar timestamps

     // Converta a lista de usuários para JSON
     String listalivrosJson = mapper.writeValueAsString(listalivros);

     // Extraia o conteúdo JSON da resposta
     String responseContent = response.getContentAsString();
     JsonNode responseJson = mapper.readTree(responseContent).get("content");

     // Converta o conteúdo extraído para JSON e compare com a lista esperada
     String contentJson = mapper.writeValueAsString(responseJson);

     assertThat(contentJson).isEqualTo(listalivrosJson); // Verifica se o conteúdo da resposta contém os dados
                                                           // esperados
}


    



}
