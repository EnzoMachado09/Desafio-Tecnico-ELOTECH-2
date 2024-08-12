package biblioteca.gestao.api.domain.googleBooks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;


//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class GoogleBooksServiceTest {

    @InjectMocks
    private GoogleBooksService googleBooksService;

    @MockBean
    private RestTemplate restTemplate;

    public GoogleBooksServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    @DisplayName("Deve retornar uma lista de livros quando a API do Google Books retorna livros")
    void RetornarLivrosQuandoAPIDevolveLivros() {
        // Dados simulados da resposta da API
        Map<String, Object> volumeInfo = new HashMap<>();
        volumeInfo.put("title", "Livro Teste");
        volumeInfo.put("authors", List.of("Autor Teste"));
        volumeInfo.put("publishedDate", "2024-08-10");
        volumeInfo.put("categories", List.of("Categoria Teste"));
        volumeInfo.put("industryIdentifiers", List.of(Map.of("type", "ISBN_13", "identifier", "1234567890123")));

        List<Map<String, Object>> items = List.of(Map.of("volumeInfo", volumeInfo));
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("items", items);

        // Simulação do comportamento do RestTemplate
        when(restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q=intitle:Livro Teste", Map.class))
                .thenReturn(resposta);

        // Chamada ao serviço
        List<DadosGoogleBooks> livros = googleBooksService.buscarLivrosPorTitulo("Livro Teste");

        // Verificações
        assertThat(livros).isNotEmpty();
    }
/* 
    @Test
    @DisplayName("Deve retornar uma lista vazia quando a API do Google Books não devolve livros")
    void RetornarListaVaziaQuandoAPIDevolveNenhumLivro() {
        // Simulação de resposta vazia
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("items", new ArrayList<>());

        // Simulação do comportamento do RestTemplate
        when(restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q=intitle:", Map.class))
                .thenReturn(resposta);

        // Chamada ao serviço
        List<DadosGoogleBooks> livros = googleBooksService.buscarLivrosPorTitulo("zzzzzzzzzzzzzzzzzzzzzz");

        // Verificações
        assertThat(livros).isEmpty();
    }
        */
}
