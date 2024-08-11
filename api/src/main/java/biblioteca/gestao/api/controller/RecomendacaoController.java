package biblioteca.gestao.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblioteca.gestao.api.domain.emprestimos.EmprestimoRepository;
import biblioteca.gestao.api.domain.livros.Livro;
import biblioteca.gestao.api.domain.livros.LivroRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

// Controller para as recomendações
@RestController
@RequestMapping("/recomendacoes")
@SecurityRequirement(name = "bearer-key")
public class RecomendacaoController {
    // Importa os repositórios necessários
    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;

    // Construtor
    public RecomendacaoController(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    // Método para recomendar livros
    @GetMapping("/{usuarioId}") 
    public ResponseEntity<List<Livro>> recomendarLivros(@PathVariable Long usuarioId) { // Recebe o id do usuário
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuarioId); // Busca as categorias dos livros emprestados pelo usuário
        if (categorias.isEmpty()) { // Se o usuário não tiver emprestado nenhum livro
            return ResponseEntity.noContent().build();// Retorna 204
        }


        // Busca os livros recomendados
        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao(categorias, usuarioId);
        if(livrosRecomendados.isEmpty()) { // Se não houver livros recomendados
            return ResponseEntity.noContent().build(); // Retorna 204
        }
        
        return ResponseEntity.ok(livrosRecomendados); // Retorna os livros recomendados
    }
}
