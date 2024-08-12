package biblioteca.gestao.api.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.dto.usuarios.DadosAtualizarUsuarios;
import biblioteca.gestao.api.dto.usuarios.DadosDetalhadosUsuarios;
import biblioteca.gestao.api.dto.usuarios.DadosUsuarios;
import biblioteca.gestao.api.modelos.usuarios.Usuario;
import biblioteca.gestao.api.repository.UsuarioRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public ResponseEntity<DadosDetalhadosUsuarios> cadastrarUsuario(DadosUsuarios dados, UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario(dados);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhadosUsuarios(usuario));
    }

    public ResponseEntity<Page<DadosDetalhadosUsuarios>> listarUsuario(Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao);
        if (page.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Page<DadosDetalhadosUsuarios> pageDto = page.map(DadosDetalhadosUsuarios::new);
        return ResponseEntity.ok(pageDto);
    }

    @Transactional
    public ResponseEntity<DadosDetalhadosUsuarios> atualizarUsuario(DadosAtualizarUsuarios dados) {
        Usuario usuario = repository.getReferenceById(dados.id());
        usuario.atualizarUsuario(dados);
        repository.save(usuario);
        return ResponseEntity.ok(new DadosDetalhadosUsuarios(usuario));
    }

    @Transactional
    public ResponseEntity<Void> deletarUsuario(Long id) {
        Usuario usuario = repository.getReferenceById(id);
        usuario.excluir();
        repository.save(usuario);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<DadosDetalhadosUsuarios> buscarUsuario(Long id) {
        Usuario usuario = repository.findByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosDetalhadosUsuarios(usuario));
    }

    public ResponseEntity<Page<DadosDetalhadosUsuarios>> buscarUsuarioNome(String nome, Pageable paginacao) {
        Page<Usuario> usuarios = repository.findByNomeAndAtivoTrue(nome, paginacao);
        Page<DadosDetalhadosUsuarios> page = usuarios.map(DadosDetalhadosUsuarios::new);
        return ResponseEntity.ok(page);
    }
}
