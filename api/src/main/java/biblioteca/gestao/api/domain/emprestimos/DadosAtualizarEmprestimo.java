package biblioteca.gestao.api.domain.emprestimos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarEmprestimo(
    @NotNull (message = "O id do emprestimo não pode ser nulo")
    Long id,
    LocalDate dataDevolucao
) {

}
