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

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public RecomendacaoController(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Livro>> recomendarLivros(@PathVariable Long usuarioId) {
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuarioId);
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao(categorias, usuarioId);
        return ResponseEntity.ok(livrosRecomendados);
    }
}
