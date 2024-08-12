package biblioteca.gestao.api.dto.usuarios;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

// 
public record DadosUsuarios(
                @NotBlank(message = "O nome do usuário não pode estar em branco") 
                String nome,

                @NotBlank(message = "O email do usuário não pode estar em branco") 
                @Email(message = "O email do usuário deve ser válido") 
                String email,

                @NotNull(message = "A data de publicação do livro não pode estar em branco")
                @PastOrPresent(message = "A data de publicação do livro não pode ser futura")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
                LocalDate data_cadastro,

                @NotBlank String telefone) {

}
