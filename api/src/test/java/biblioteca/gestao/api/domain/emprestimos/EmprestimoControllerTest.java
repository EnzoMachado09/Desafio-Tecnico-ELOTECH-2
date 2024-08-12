package biblioteca.gestao.api.domain.emprestimos;

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

import biblioteca.gestao.api.dto.emprestimos.DadosAtualizarEmprestimo;
import biblioteca.gestao.api.dto.emprestimos.DadosDetalhadosEmprestimo;
import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;
import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.modelos.usuarios.Usuario;
import biblioteca.gestao.api.repository.EmprestimoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mvc;

    //@Autowired
    //private JacksonTester<DadosEmprestimos> dadosEmprestimoJson;

    @Autowired
    private JacksonTester<DadosDetalhadosEmprestimo> dadosDetalhadosEmprestimoJson;

    @Autowired
    private JacksonTester<Emprestimo> emprestimoJson;

    @Autowired
    private JacksonTester<DadosAtualizarEmprestimo> dadosAtualizarJson;

    @MockBean
    private EmprestimoRepository repository;

    

    @Test
    @DisplayName("Deve devolver o código 400 ao tentar cadastrar um Empréstimo com dados inválidos")
    @WithMockUser // Simula um usuário autenticado
    void cadastrarEmprestimoCase1() throws Exception {
        // Act
        var response = mvc.perform(post("/emprestimos")) // Envia uma requisição POST para /emprestimos usando o MockMvc
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // Verifica se o status da resposta  é 400
    }
/* 
    @Test
    @DisplayName("Deve devolver o código 201 ao tentar cadastrar um Empréstimo com dados válidos")
    @WithMockUser // Simula um usuário autenticado
    void cadastrarEmprestimoCase2() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data de empréstimo

        Livro livro = new Livro(20L, "Livro Teste", "autor teste", "123", data, true, "categoria teste");
        Usuario usuario = new Usuario(21L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);

        var emprestimo = new DadosEmprestimos(1L, 1L, data); // Cria os dados do empréstimo

        // Act
        var response = mvc.perform(post("/emprestimos")
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como JSON
                .content(dadosEmprestimoJson.write(emprestimo).getJson()) // Converte os dados do empréstimo para JSON
        ).andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()); // Verifica se o status da resposta é 201
        var jsonEsperado = dadosDetalhadosEmprestimoJson.write(new DadosDetalhadosEmprestimo(
                1L, 1L, 1L, usuario.getNome(), livro.getTitulo(), data, data)).getJson(); // Dados esperados

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se a resposta contém os dados esperados
    }
    */

    @Test
    @DisplayName("Deve devolver o código 200 ao listar todos os Empréstimos ativos")
    @WithMockUser // Simula um usuário autenticado
    void listarEmprestimosAtivosCase1() throws Exception {
        //Arrange
        var data = LocalDate.now(); // Cria uma data de empréstimo

        // Cria os objetos necessários
        Livro livro1 = new Livro(1L, "Livro Teste 1", "autor teste 1", "123", data, true, "categoria teste 1");
        Usuario usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);
        Livro livro2 = new Livro(2L, "Livro Teste 2", "autor teste 2", "567", data, true, "categoria teste 2");
        Usuario usuario2 = new Usuario(2L, "Usuario Teste 2", "teste2@hotmail.com", data, "456", true);

        // Cria os empréstimos
        Emprestimo emprestimo1 = new Emprestimo(usuario1, livro1, data);
        Emprestimo emprestimo2 = new Emprestimo(usuario2, livro2, data);

        // Cria a lista de empréstimos e o PageImpl
        var listaEmprestimos = List.of(emprestimo1, emprestimo2);
        when(repository.findAllByStatus(eq("ATIVO"), any(Pageable.class))).thenReturn(new PageImpl<>(listaEmprestimos));

        // Act
        var response = mvc.perform(get("/emprestimos/ativos")
                .param("page", "0")
                .param("size", "10"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200 OK

        // Configura o ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Converte a lista de empréstimos para JSON
        String listaEmprestimosJson = mapper.writeValueAsString(listaEmprestimos);
        String responseContent = response.getContentAsString();
        JsonNode responseJson = mapper.readTree(responseContent).get("content");
        String contentJson = mapper.writeValueAsString(responseJson);

        // Verifica se o conteúdo da resposta é igual ao JSON esperado
        assertThat(contentJson).isEqualTo(listaEmprestimosJson);
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao listar todos os Empréstimos devolvidos")
    @WithMockUser // Simula um usuário autenticado
    void listarEmprestimosAtivosCase2() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data de empréstimo

        // Cria os objetos necessários
        Livro livro1 = new Livro(1L, "Livro Teste 1", "autor teste 1", "123", data, true, "categoria teste 1");
        Usuario usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);
        Livro livro2 = new Livro(2L, "Livro Teste 2", "autor teste 2", "567", data, true, "categoria teste 2");
        Usuario usuario2 = new Usuario(2L, "Usuario Teste 2", "teste2@hotmail.com", data, "456", true);

        // Cria os empréstimos
        Emprestimo emprestimo1 = new Emprestimo(usuario1, livro1, data);
        Emprestimo emprestimo2 = new Emprestimo(usuario2, livro2, data);

        // Cria a lista de empréstimos e o PageImpl
        var listaEmprestimos = List.of(emprestimo1, emprestimo2);
        when(repository.findAllByStatus(eq("DEVOLVIDO"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(listaEmprestimos));

        // Act
        var response = mvc.perform(get("/emprestimos/devolvidos")
                .param("page", "0")
                .param("size", "10"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Configura o ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Converte a lista de empréstimos para JSON
        String listaEmprestimosJson = mapper.writeValueAsString(listaEmprestimos);
        String responseContent = response.getContentAsString();
        JsonNode responseJson = mapper.readTree(responseContent).get("content");
        String contentJson = mapper.writeValueAsString(responseJson);

        assertThat(contentJson).isEqualTo(listaEmprestimosJson);
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao buscar um Empréstimo por ID")
    @WithMockUser // Simula um usuário autenticado
    void buscarEmprestimoPorId() throws Exception {
        //Arrange
        var data = LocalDate.now(); // Cria uma data de empréstimo

        // Cria os objetos necessários
        Livro livro = new Livro(1L, "Livro Teste 1", "autor teste 1", "123", data, true, "categoria teste 1");
        Usuario usuario = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);

        // Cria o empréstimo com um ID específico
        Emprestimo emprestimo = new Emprestimo(1L, usuario, livro, data, data, "ATIVO");

        // Configura o mock para o repositório
        when(repository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));

        // Act
        var response = mvc.perform(get("/emprestimos/id/{id}", emprestimo.getId()))
                .andReturn().getResponse();

        // Assert
        // Verifica se o status da resposta é 200 OK
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = emprestimoJson.write(new Emprestimo(
                emprestimo.getId(),
                emprestimo.getUsuario(),
                emprestimo.getLivro(),
                data,
                data, "ATIVO")).getJson();

        // Verifica se o JSON da resposta é igual ao esperado
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


@Test
@DisplayName("Deve devolver o código 200 ao atualizar um Empréstimo com dados válidos")
@WithMockUser // Simula um usuário autenticado
void atualizarEmprestimo() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data de empréstimo

    // Cria os objetos necessários
    Livro livro = new Livro(1L, "Livro Teste", "autor teste", "123", data, true, "categoria teste");
    Usuario usuario = new Usuario(1L, "Usuario Teste", "teste1@hotmail.com", data, "123", true);

    // Cria o empréstimo com um ID específico
    Emprestimo emprestimo = new Emprestimo(usuario, livro, data);

    // Dados para atualizar o empréstimo
    DadosAtualizarEmprestimo dadosAtualizar = new DadosAtualizarEmprestimo(emprestimo.getId(), data);

    // Configura o mock para o repositório
    when(repository.getReferenceById(emprestimo.getId())).thenReturn(emprestimo);

    // Act
    // Executa a requisição PUT
    var response = mvc.perform(put("/emprestimos")
            .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como JSON
            .content(dadosAtualizarJson.write(dadosAtualizar).getJson()) // Converte os dados de atualização para JSON
    ).andReturn().getResponse();

    // Assert
    // Verifica se o status da resposta é 200 OK
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    // Atualiza o empréstimo com a data de devolução
    emprestimo.devolverLivro(data);

    var jsonEsperado = dadosDetalhadosEmprestimoJson.write(new DadosDetalhadosEmprestimo(
            emprestimo.getId(),
            emprestimo.getUsuario().getId(),
            emprestimo.getLivro().getId(),
            emprestimo.getUsuario().getNome(),
            emprestimo.getLivro().getTitulo(),
            data,
            data)).getJson();

    // Verifica se o JSON da resposta é igual ao esperado
    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
}


    



}