package biblioteca.gestao.api.domain.usuarios;

public record DadosDetalhadosUsuarios (
        Long id,
        String nome,
        String email,
        String data_cadastro,
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
