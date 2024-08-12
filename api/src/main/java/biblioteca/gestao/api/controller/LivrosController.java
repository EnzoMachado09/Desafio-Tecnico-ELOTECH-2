package biblioteca.gestao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.dto.livros.DadosAtualizarLivros;
import biblioteca.gestao.api.dto.livros.DadosDetalhadosLivros;
import biblioteca.gestao.api.dto.livros.DadosLivros;
import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.servicos.LivrosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
@SecurityRequirement(name = "bearer-key")
public class LivrosController {

    @Autowired
    private LivrosService livrosService;

    @PostMapping
    public ResponseEntity<DadosDetalhadosLivros> cadastrarLivro(@RequestBody @Valid DadosLivros dados,
            UriComponentsBuilder uriBuilder) {
        var detalhesLivro = livrosService.cadastrarLivro(dados, uriBuilder);
        var uri = uriBuilder.path("/livros/{id}").buildAndExpand(detalhesLivro.id()).toUri();
        return ResponseEntity.created(uri).body(detalhesLivro);
    }

    @GetMapping
    public ResponseEntity<Page<Livro>> listarLivro(@PageableDefault(size = 10, sort = { "titulo" }) Pageable paginacao) {
        var page = livrosService.listarLivro(paginacao);
        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(page);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhadosLivros> atualizarLivro(@RequestBody @Valid DadosAtualizarLivros dados) {
        var detalhesLivro = livrosService.atualizarLivro(dados);
        return ResponseEntity.ok(detalhesLivro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livrosService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DadosDetalhadosLivros> buscarLivro(@PathVariable Long id) {
        var detalhesLivro = livrosService.buscarLivroPorId(id);
        return ResponseEntity.ok(detalhesLivro);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosDetalhadosLivros>> buscarLivroNome(@PathVariable String titulo,
            @PageableDefault(size = 10, sort = { "titulo" }) Pageable paginacao) {
        var page = livrosService.buscarLivroPorTitulo(titulo, paginacao);
        return ResponseEntity.ok(page);
    }
}
