package biblioteca.gestao.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.domain.emprestimos.DadosAtualizarEmprestimo;
import biblioteca.gestao.api.domain.emprestimos.DadosDetalhadosEmprestimo;
import biblioteca.gestao.api.domain.emprestimos.DadosEmprestimos;
import biblioteca.gestao.api.domain.emprestimos.Emprestimo;
import biblioteca.gestao.api.domain.emprestimos.EmprestimoRepository;
import biblioteca.gestao.api.domain.livros.Livro;
import biblioteca.gestao.api.domain.livros.LivroRepository;
import biblioteca.gestao.api.domain.usuarios.Usuario;
import biblioteca.gestao.api.domain.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;

// Controller para os empréstimos
@RestController
@RequestMapping("/emprestimos")
public class EmprestimosController {

    // Injeção de dependências
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // cadastrar um emprestimo no banco de dados
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhadosEmprestimo> cadastrarEmprestimo(@RequestBody DadosEmprestimos dados,
            UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioRepository.getReferenceById(dados.usuarioId());
        Livro livro = livroRepository.getReferenceById(dados.livroId());

        // Verifica se o livro já está emprestado
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.findAllByLivroIdAndStatus(livro.getId(), "ATIVO");
        if (!emprestimosAtivos.isEmpty()) {
            throw new RuntimeException("Livro já está emprestado.");
        }

        // Se o livro não estiver emprestado, cria um novo empréstimo
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dados.dataEmprestimo());
        emprestimoRepository.save(emprestimo);

        // Cria a URI para o novo empréstimo
        var uri = uriBuilder.path("/emprestimos/{id}").buildAndExpand(emprestimo.getId()).toUri();

        // retorna o status 201 Created e o novo empréstimo
        return ResponseEntity.created(uri).body(new DadosDetalhadosEmprestimo(emprestimo));
    }

    // atualizar um emprestimo no banco de dados (entrega do livro)
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhadosEmprestimo> atualizarEmprestimo(@RequestBody DadosAtualizarEmprestimo dados) {
        Emprestimo emprestimo = emprestimoRepository.getReferenceById(dados.id());
        emprestimo.devolverLivro(dados.dataDevolucao());
        emprestimoRepository.save(emprestimo);

        // retorna o status 200 OK e o empréstimo atualizado
        return ResponseEntity.ok(new DadosDetalhadosEmprestimo(emprestimo));
    }

    // buscar um emprestimo no banco de dados pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimo(@PathVariable Long id) {
        var busca = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));

        // retorna o status 200 OK e o empréstimo buscado
        return ResponseEntity.ok(busca);
    }

    // listar os emprestimos devolvidos
    @GetMapping("/devolvidos") // paginação padrão de 10 em 10 e ordenado pelo id dos empréstimos
    public ResponseEntity<Page<Emprestimo>> listarEmprestimosDevolvidos(
            @PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
        Page<Emprestimo> page = emprestimoRepository.findAllByStatus("DEVOLVIDO", paginacao);

        // retorna o status 200 OK e a lista de empréstimos devolvidos
        return ResponseEntity.ok(page);
    }

    // listar os emprestimos ativos
    @GetMapping("/ativos") // paginação padrão de 10 em 10 e ordenado pelo id dos empréstimos
    public ResponseEntity<Page<Emprestimo>> listarEmprestimosAtivos(
            @PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
        Page<Emprestimo> page = emprestimoRepository.findAllByStatus("ATIVO", paginacao);

        // retorna o status 200 OK e a lista de empréstimos ativos
        return ResponseEntity.ok(page);
    }
}
