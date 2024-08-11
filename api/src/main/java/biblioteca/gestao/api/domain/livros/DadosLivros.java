package biblioteca.gestao.api.domain.livros;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record DadosLivros(
        @NotBlank(message = "O titulo do livro não pode estar em branco") String titulo,

        @NotBlank(message = "O autor do livro não pode estar em branco") String autor,

        @NotBlank(message = "O ISBN do livro não pode estar em branco") String isbn,

        @NotNull(message = "A data de publicação do livro não pode estar em branco")
        @PastOrPresent(message = "A data de publicação do livro não pode ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
        LocalDate data_publicacao,

        @NotNull(message = "A categoria do livro não pode ser nula") String categoria) {

}
