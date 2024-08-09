package biblioteca.gestao.api.domain.livros;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosLivros(

        @NotBlank (message = "O titulo do livro não pode estar em branco")
        String titulo,

        @NotBlank  (message = "O autor do livro não pode estar em branco")
        String autor,

        @NotBlank (message = "O ISBN do livro não pode estar em branco") 
        String isbn,

        @NotBlank (message = "A data de publicação do livro não pode estar em branco")  
        String data_publicacao,

        @NotNull (message = "A categoria do livro não pode ser nula") 
        Categoria categoria
        ) {

    

}
