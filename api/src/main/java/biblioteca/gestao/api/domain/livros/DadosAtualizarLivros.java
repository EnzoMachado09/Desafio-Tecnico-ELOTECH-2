package biblioteca.gestao.api.domain.livros;

import java.time.LocalDate;


import jakarta.validation.constraints.NotNull;

// DTO para atualizar os dados de um livro
public record DadosAtualizarLivros(
                @NotNull Long id,
                String titulo,
                String autor,
                String isbn,
                LocalDate data_publicacao,
                String categoria) {

}
