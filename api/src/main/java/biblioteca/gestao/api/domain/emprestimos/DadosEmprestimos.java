package biblioteca.gestao.api.domain.emprestimos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DadosEmprestimos (
    @NotNull (message = "O id do usuario não pode ser nulo")
    Long usuarioId,

    @NotNull (message = "O id do livro não pode ser nulo")
    Long livroId,

    @NotNull (message = "A data de emprestimo não pode ser nula")
    LocalDate dataEmprestimo
) {

}
