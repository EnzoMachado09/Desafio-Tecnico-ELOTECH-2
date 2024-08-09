package biblioteca.gestao.api.domain.usuarios;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarUsuarios(
        @NotNull Long id,
        String nome,
        String email,
        String data_cadastro,
        String telefone) {

}
