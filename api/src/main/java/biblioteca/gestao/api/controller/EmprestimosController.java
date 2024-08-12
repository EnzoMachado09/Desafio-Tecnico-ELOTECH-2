package biblioteca.gestao.api.controller;

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

import biblioteca.gestao.api.dto.emprestimos.DadosAtualizarEmprestimo;
import biblioteca.gestao.api.dto.emprestimos.DadosDetalhadosEmprestimo;
import biblioteca.gestao.api.dto.emprestimos.DadosEmprestimos;
import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;
import biblioteca.gestao.api.servicos.EmprestimosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/emprestimos")
@SecurityRequirement(name = "bearer-key")
public class EmprestimosController {

    @Autowired
    private EmprestimosService emprestimosService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhadosEmprestimo> cadastrarEmprestimo(@RequestBody DadosEmprestimos dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhadosEmprestimo emprestimo = emprestimosService.cadastrarEmprestimo(dados);
        var uri = uriBuilder.path("/emprestimos/{id}").buildAndExpand(emprestimo.id()).toUri();
        return ResponseEntity.created(uri).body(emprestimo);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhadosEmprestimo> atualizarEmprestimo(@RequestBody DadosAtualizarEmprestimo dados) {
        DadosDetalhadosEmprestimo emprestimo = emprestimosService.atualizarEmprestimo(dados);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoId(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimosService.buscarEmprestimoId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/devolvidos")
    public ResponseEntity<Page<Emprestimo>> listarEmprestimosDevolvidos(
            @PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
        Page<Emprestimo> page = emprestimosService.listarEmprestimosPorStatus("DEVOLVIDO", paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<Emprestimo>> listarEmprestimosAtivos(
            @PageableDefault(size = 10, sort = { "id" }) Pageable paginacao) {
        Page<Emprestimo> page = emprestimosService.listarEmprestimosPorStatus("ATIVO", paginacao);
        return ResponseEntity.ok(page);
    }
}
