package biblioteca.gestao.api.controller;

import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.servicos.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
@SecurityRequirement(name = "bearer-key")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Livro>> recomendarLivros(@PathVariable Long id) {
        List<Livro> livrosRecomendados = recomendacaoService.recomendarLivros(id);

        // Se não há livros recomendados, retorna 204 No Content
        if (livrosRecomendados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Caso contrário, retorna 200 OK com a lista de livros recomendados
        return ResponseEntity.ok(livrosRecomendados);
    }
}
