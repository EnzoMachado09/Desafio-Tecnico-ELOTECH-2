package biblioteca.gestao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.dto.usuarios.DadosAtualizarUsuarios;
import biblioteca.gestao.api.dto.usuarios.DadosDetalhadosUsuarios;
import biblioteca.gestao.api.dto.usuarios.DadosUsuarios;
import biblioteca.gestao.api.servicos.UsuariosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhadosUsuarios> cadastrarUsuario(@RequestBody @Valid DadosUsuarios dados, UriComponentsBuilder uriBuilder) {
        return usuariosService.cadastrarUsuario(dados, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhadosUsuarios>> listarUsuario(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        return usuariosService.listarUsuario(paginacao);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhadosUsuarios> atualizarUsuario(@RequestBody @Valid DadosAtualizarUsuarios dados) {
        return usuariosService.atualizarUsuario(dados);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        return usuariosService.deletarUsuario(id);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DadosDetalhadosUsuarios> buscarUsuario(@PathVariable Long id) {
        return usuariosService.buscarUsuario(id);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<DadosDetalhadosUsuarios>> buscarUsuarioNome(
            @PathVariable String nome,
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        return usuariosService.buscarUsuarioNome(nome, paginacao);
    }
}
