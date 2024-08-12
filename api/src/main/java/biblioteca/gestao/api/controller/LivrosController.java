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

import biblioteca.gestao.api.domain.livros.DadosAtualizarLivros;
import biblioteca.gestao.api.domain.livros.DadosDetalhadosLivros;
import biblioteca.gestao.api.domain.livros.DadosLivros;
import biblioteca.gestao.api.domain.livros.Livro;
import biblioteca.gestao.api.domain.livros.LivroRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
@SecurityRequirement(name = "bearer-key")
public class LivrosController {

    // Injeção de dependências
    @Autowired
    private LivroRepository repository;

    // cadastrar um livro no banco de dados
    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhadosLivros> cadastrarLivro(@RequestBody @Valid DadosLivros dados,
            UriComponentsBuilder uriBuilder) {
        Livro livro = new Livro(dados);
        repository.save(livro);

        // cria a URI para o novo livro
        var uri = uriBuilder.path("/livros/{id}").buildAndExpand(livro.getId()).toUri();

        // retorna o status 201 Created e o novo livro
        return ResponseEntity.created(uri).body(new DadosDetalhadosLivros(livro));

    }

    // listar os livros usando o Pageable, deixando os livros ordenados por titulo e
    // apenas 10 livros por página ordenados por titulo
    @GetMapping
    public ResponseEntity<Page<Livro>> listarLivro(
            @PageableDefault(size = 10, sort = { "titulo" }) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao);

        if (page.isEmpty()) { // se a lista de livros estiver vazia retorna o status 204 No Content
            return ResponseEntity.noContent().build();
        }

        // retorna o status 200 OK e a lista de livros
        return ResponseEntity.ok(page);
    }

    // atualizar um livro no banco de dados
    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhadosLivros> atualizarLivro(@RequestBody @Valid DadosAtualizarLivros dados) {
        Livro livro = repository.getReferenceById(dados.id());
        livro.atualizarLivro(dados);
        repository.save(livro);

        // retorna o status 200 OK e o livro atualizado
        return ResponseEntity.ok(new DadosDetalhadosLivros(livro));
    }

    // deletar um livro no banco de dados
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> deletarLivro(@PathVariable Long id) {
        Livro livro = repository.getReferenceById(id);
        livro.excluir();
        repository.save(livro);

        // retorna o status 204 No Content
        return ResponseEntity.noContent().build();
    }

    // buscar um livro no banco de dados pelo id
    @GetMapping("/id/{id}")
    public ResponseEntity<DadosDetalhadosLivros> buscarLivro(@PathVariable Long id) {
        Livro livro = repository.findByIdAndAtivoTrue(id);

        // retorna o status 200 OK e o livro buscado
        return ResponseEntity.ok(new DadosDetalhadosLivros(livro));
    }

    // buscar um Livro no banco de dados pelo nome
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<DadosDetalhadosLivros>> buscarLivroNome(
            @PathVariable String titulo,
            @PageableDefault(size = 10, sort = { "titulo" }) Pageable paginacao) {

        // Busca a página de Livros
        Page<Livro> livros = repository.findByTituloAndAtivoTrue(titulo, paginacao);

        // Transforma a página de Livro em uma página de DadosDetalhadosLivros
        Page<DadosDetalhadosLivros> page = livros.map(DadosDetalhadosLivros::new);

        // Retorna o status 200 OK e a página de DadosDetalhadosLivros
        return ResponseEntity.ok(page);
    }

}
