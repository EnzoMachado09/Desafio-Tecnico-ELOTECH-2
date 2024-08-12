package biblioteca.gestao.api.dto.usuarios;

import java.time.LocalDate;

import biblioteca.gestao.api.modelos.usuarios.Usuario;

// DTO para detalhar os dados de um usuário
public record DadosDetalhadosUsuarios (
        Long id,
        String nome,
        String email,
        LocalDate data_cadastro,
        String telefone
        
) {
    public DadosDetalhadosUsuarios(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getData_cadastro(),
                usuario.getTelefone());
    }

}
