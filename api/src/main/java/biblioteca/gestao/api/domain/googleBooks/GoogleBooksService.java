package biblioteca.gestao.api.domain.googleBooks;

// Importações necessárias
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service // Anotação que define esta classe como um serviço Spring
public class GoogleBooksService {

    // URL base da API do Google Books
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";

    @SuppressWarnings("unchecked") // Ignora avisos relacionados ao uso de tipos não verificados

    public List<DadosGoogleBooks> buscarLivrosPorTitulo(String titulo) {
        // Construir a URL para a chamada à API com o parâmetro do título
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_BOOKS_API_URL)
                .queryParam("q", "intitle:" + titulo) // Adiciona o parâmetro de busca para o título
                .toUriString(); // Converte para uma string de URL

        // Inicializa RestTemplate para realizar a chamada HTTP à API do Google Books
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> resposta;
        try {
            // Faz a chamada à API e obtém a resposta como um Map
            resposta = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            // Em caso de exceção (por exemplo, erro de rede), imprime o stack trace e retorna uma lista vazia
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Verifica se a resposta é nula ou não contém a chave "items" (indicando que não há livros)
        if (resposta == null || !resposta.containsKey("items")) {
            // Retorna uma lista vazia se não houver itens
            return new ArrayList<>();
        }

        // Lista para armazenar os dados dos livros encontrados
        List<DadosGoogleBooks> livros = new ArrayList<>();
        // Obtem a lista de itens da resposta
        List<Map<String, Object>> items = (List<Map<String, Object>>) resposta.get("items");

        // Passa sobre cada item na lista de itens
        for (Map<String, Object> item : items) {
            // Obtem as informações do livro no volumeInfo
            Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");
            if (volumeInfo == null) {
                continue; // Se não houver informações do volume, pula para o próximo item
            }

            // Extrai o titulo do livro
            String tituloLivro = (String) volumeInfo.get("title");
            // Obtem a lista de autores e pega o primeiro autor, se disponivel
            List<?> authorsList = (List<?>) volumeInfo.get("authors");
            String autor = (authorsList != null && !authorsList.isEmpty()) ? (String) authorsList.get(0) : null;

            String isbn = null;
            // Obtém a lista de identificadores e procura pelo ISBN_13
            List<Map<String, String>> identifiers = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
            if (identifiers != null) { // Se houver identificadores
                isbn = identifiers.stream() // Converte a lista em um stream
                        .filter(id -> "ISBN_13".equals(id.get("type"))) // Filtra pelo tipo ISBN_13
                        .map(id -> id.get("identifier")) // Obtém o identificador
                        .findFirst() // Pega o primeiro resultado encontrado
                        .orElse(null); // Se não encontrar, retorna null
            }

            // Obtem a data de publicação
            String dataPublicacao = (String) volumeInfo.get("publishedDate");

            // Obtem a lista de categorias e pega a primeira, se disponível
            List<String> categorias = (List<String>) volumeInfo.get("categories");
            String categoria = (categorias != null && !categorias.isEmpty()) ? categorias.get(0) : null;

            // Cria um novo objeto DadosGoogleBooks com as informações extraídas e adiciona à lista
            livros.add(new DadosGoogleBooks(tituloLivro, autor, isbn, dataPublicacao, categoria));
        }

        // Retorna a lista de livros encontrados ja no formato do Livro da minha API
        return livros;
    }
}
