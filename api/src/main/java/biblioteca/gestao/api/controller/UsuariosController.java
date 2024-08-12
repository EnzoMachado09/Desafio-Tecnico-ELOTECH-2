package biblioteca.gestao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.domain.usuarios.DadosAtualizarUsuarios;
import biblioteca.gestao.api.domain.usuarios.DadosDetalhadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.DadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.Usuario;
import biblioteca.gestao.api.domain.usuarios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuariosController {

    // Injeção de dependências
    @Autowired
    private UsuarioRepository repository;

    // cadastrar um usuario no banco de dados
    @Transactional
    @PostMapping
    public ResponseEntity<DadosDetalhadosUsuarios> cadastrarUsuario(@RequestBody @Valid DadosUsuarios dados,
            UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario(dados);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        // retorna o status 201 Created e o novo usuario
        return ResponseEntity.created(uri).body(new DadosDetalhadosUsuarios(usuario));

    }

    // listar os Usuarios usando o Pageable, deixando os Usuarios ordenados por
    // titulo e apenas 10 Usuarios por página ordenados por titulo
    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuario(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao);

        if (page.isEmpty()) {// se a lista de Usuarios estiver vazia retorna o status 204 No Content
            return ResponseEntity.noContent().build();
        }

        // retorna o status 200 OK e a lista de Usuarios
        return ResponseEntity.ok(page);
    }

    // atualizar um Usuario no banco de dados
    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhadosUsuarios> atualizarUsuario(@RequestBody @Valid DadosAtualizarUsuarios dados) {
        Usuario usuario = repository.getReferenceById(dados.id());
        usuario.atualizarUsuario(dados);
        repository.save(usuario);

        // retorna o status 200 OK e o Usuario atualizado
        return ResponseEntity.ok(new DadosDetalhadosUsuarios(usuario));
    }

    // deletar um usuario no banco de dados
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id) {
        Usuario usuario = repository.getReferenceById(id);
        usuario.excluir();
        repository.save(usuario);

        // retorna o status 204 No Content
        return ResponseEntity.noContent().build();
    }

    // buscar um Usuario no banco de dados pelo id
    @GetMapping("/id/{id}")
    public ResponseEntity<DadosDetalhadosUsuarios> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = repository.findByIdAndAtivoTrue(id);

        // retorna o status 200 OK e o Usuario buscado
        return ResponseEntity.ok(new DadosDetalhadosUsuarios(usuario));
    }

    // buscar um Usuario no banco de dados pelo nome
    @GetMapping("/nome/{nome}")
public ResponseEntity<Page<DadosDetalhadosUsuarios>> buscarUsuarioNome(
        @PathVariable String nome, 
        @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao ) {
    
    // Busca a página de usuarios
    Page<Usuario> usuarios = repository.findByNomeAndAtivoTrue(nome, paginacao);

    // Transforma a página de Usuario em uma página de DadosDetalhadosUsuarios
    Page<DadosDetalhadosUsuarios> page = usuarios.map(DadosDetalhadosUsuarios::new);

    // Retorna o status 200 OK e a página de DadosDetalhadosUsuarios
    return ResponseEntity.ok(page);
}
}
