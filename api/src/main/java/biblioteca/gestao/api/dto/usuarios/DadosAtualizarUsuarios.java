package biblioteca.gestao.api.dto.usuarios;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

// DTO para atualizar os dados de um usuário
public record DadosAtualizarUsuarios(
        @NotNull Long id,
        String nome,
        String email,
        LocalDate data_cadastro,
        String telefone) {

}
