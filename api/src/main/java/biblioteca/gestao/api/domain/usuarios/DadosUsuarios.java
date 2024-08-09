package biblioteca.gestao.api.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosUsuarios(
                @NotBlank(message = "O nome do usuário não pode estar em branco") String nome,

                @NotBlank(message = "O email do usuário não pode estar em branco") @Email(message = "O email do usuário deve ser válido") String email,

                @NotBlank(message = "A data de cadastro do usuário não pode estar em branco") String data_cadastro,

                @NotBlank String telefone) {

}
