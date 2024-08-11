package biblioteca.gestao.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import biblioteca.gestao.api.domain.googleBooks.DadosGoogleBooks;
import biblioteca.gestao.api.domain.googleBooks.GoogleBooksService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/googlebooks")
@SecurityRequirement(name = "bearer-key")
public class GoogleBooksController {

    @Autowired // Injeção de dependência
    private GoogleBooksService googleBooksService;

    @GetMapping("/buscar/{titulo}") // Busca um livro por título
    public ResponseEntity<List<DadosGoogleBooks>> buscarLivroPorTitulo(@PathVariable String titulo) {
        // Chama o método buscarLivrosPorTitulo do serviço GoogleBooksService
        // Passa o título recebido na URL como argumento para buscar os livros
        // correspondentes
        List<DadosGoogleBooks> resultado = googleBooksService.buscarLivrosPorTitulo(titulo);

        // Retorna a lista de livros encontrados como uma resposta HTTP com status 200
        // OK
        return ResponseEntity.ok(resultado);
    }
}
