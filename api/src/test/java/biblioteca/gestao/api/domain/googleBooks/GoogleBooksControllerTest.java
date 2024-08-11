package biblioteca.gestao.api.domain.googleBooks;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import biblioteca.gestao.api.controller.GoogleBooksController;


import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class GoogleBooksControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private GoogleBooksController googleBooksController;

    @Mock
    private GoogleBooksService googleBooksService;

    @BeforeEach 
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        mvc = MockMvcBuilders.standaloneSetup(googleBooksController).build(); // Inicializa o MockMvc para testes do controller
    }

    @Test
    @DisplayName("Deve devolver 200 e uma lista de livros quando a busca retorna livros")
    @WithMockUser // Simula um usuário autenticado
    void buscarLivroPorTituloCase1() throws Exception {
        // Arrange
        // Dados simulados
        DadosGoogleBooks livro = new DadosGoogleBooks("Livro Teste", "Autor Teste", "1234567890123", "2024-08-10", "Categoria Teste");
        List<DadosGoogleBooks> livros = List.of(livro);

        // Act
        // Simulação do comportamento do serviço
        when(googleBooksService.buscarLivrosPorTitulo("Livro Teste")).thenReturn(livros);

        // Configuração do ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var response = mvc.perform(get("/googlebooks/buscar/{titulo}", "Livro Teste")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        // Verifica se o conteúdo da resposta é igual ao JSON esperado
        assertThat(response.getContentAsString()).isEqualTo(mapper.writeValueAsString(livros));

                
    }

    @Test
    @DisplayName("Deve devolver 200 e uma lista vazia quando a busca não retorna livros")
    @WithMockUser // Simula um usuário autenticado
    void buscarLivroPorTituloCase2() throws Exception {
        // Arrange
        // Simulação de lista vazia
        List<DadosGoogleBooks> livros = List.of();

        // Act
        // Simulação do comportamento do serviço
        when(googleBooksService.buscarLivrosPorTitulo("Livro Teste")).thenReturn(livros);

        // Configuração do ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Requisição e verificação
        var response = mvc.perform(get("/googlebooks/buscar/{titulo}", "Livro Teste")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        // Verifica se o conteúdo da resposta é igual ao JSON esperado
        assertThat(response.getContentAsString()).isEqualTo(mapper.writeValueAsString(livros));
    }
}
