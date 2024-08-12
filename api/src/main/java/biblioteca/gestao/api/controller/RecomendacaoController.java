package biblioteca.gestao.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.servicos.RecomendacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/recomendacoes")
@SecurityRequirement(name = "bearer-key")
public class RecomendacaoController {

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoController(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Livro>> recomendarLivros(@PathVariable Long usuarioId) {
        List<Livro> livrosRecomendados = recomendacaoService.recomendarLivros(usuarioId);
        if (livrosRecomendados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(livrosRecomendados);
    }
}
