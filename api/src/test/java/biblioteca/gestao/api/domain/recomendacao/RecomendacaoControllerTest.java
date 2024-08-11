package biblioteca.gestao.api.domain.recomendacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import biblioteca.gestao.api.controller.RecomendacaoController;
import biblioteca.gestao.api.domain.emprestimos.EmprestimoRepository;
import biblioteca.gestao.api.domain.livros.Livro;
import biblioteca.gestao.api.domain.livros.LivroRepository;
import biblioteca.gestao.api.domain.usuarios.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RecomendacaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private RecomendacaoController recomendacaoController;

    public RecomendacaoControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(recomendacaoController).build();
    }

    @Test
@DisplayName("Deve devolver 200 e uma lista de livros recomendados com formato de data correto")
void recomendarLivrosComLivrosDisponiveis() throws Exception {
    // Arrange 
    Livro livro1 = new Livro(1L, "Livro Recomendado 1", "Autor 1", "123456789", null, true, "Categoria 1");
    Livro livro2 = new Livro(2L, "Livro Recomendado 2", "Autor 2", "987654321", null, true, "Categoria 1");

    Usuario usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", null, "123", true);

    var categorias = List.of("Categoria 1");
    var livrosRecomendados = List.of(livro1, livro2);

    // Simula o comportamento dos repositórios
    when(emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario1.getId())).thenReturn(categorias);
    when(livroRepository.findLivrosParaRecomendacao(categorias, usuario1.getId())).thenReturn(livrosRecomendados);

    // Configura o ObjectMapper para lidar com LocalDate
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    // Converte a lista de livros recomendados para JSON
    String livrosJson = mapper.writeValueAsString(livrosRecomendados);

    // Act
    var response = mvc.perform(get("/recomendacoes/{id}", usuario1.getId()))
            .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(livrosJson);
}


    @Test
    @DisplayName("Deve devolver 204 quando o usuário tem livros emprestados, mas não há livros recomendados disponíveis")
    void recomendarLivrosSemLivrosDisponiveis() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", null, "123", true);

        var categorias = List.of("Categoria Inexistente");
        

        // Simula o comportamento dos repositórios
        when(emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario1.getId())).thenReturn(categorias);
        when(livroRepository.findLivrosParaRecomendacao(categorias, usuario1.getId())).thenReturn(List.of());

        // Act
        var response = mvc.perform(get("/recomendacoes/1"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deve devolver 204 quando o usuário não tem livros emprestados")
    void recomendarLivrosSemLivrosEmprestados() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", null, "123", true);

        // Simula o comportamento dos repositórios
        when(emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario1.getId())).thenReturn(List.of());

        // Act
        var response = mvc.perform(get("/recomendacoes/1"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
