package biblioteca.gestao.api.domain.emprestimos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

// DTO para atualizar um emprestimo
public record DadosAtualizarEmprestimo(
        @NotNull(message = "O id do emprestimo não pode ser nulo") 
        Long id,
        
        @NotNull(message = "A data de devolução não pode ser nula") 
        @PastOrPresent(message = "A data de devolução não pode ser futura") 
        @JsonFormat(pattern = "yyyy-MM-dd") 
        LocalDate dataDevolucao) {

}
