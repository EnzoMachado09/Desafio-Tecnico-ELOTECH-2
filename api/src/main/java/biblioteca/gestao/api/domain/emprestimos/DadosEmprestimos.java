package biblioteca.gestao.api.domain.emprestimos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

// DTO para emprestimos
public record DadosEmprestimos (
    @NotNull (message = "O id do usuario não pode ser nulo")
    Long usuarioId,

    @NotNull (message = "O id do livro não pode ser nulo")
    Long livroId,

    @NotNull (message = "A data de emprestimo não pode ser nula")
    @PastOrPresent (message = "A data de emprestimo deve ser no passado ou presente")
    @JsonFormat(pattern = "yyyy-MM-dd") 
    LocalDate dataEmprestimo
) {

}
