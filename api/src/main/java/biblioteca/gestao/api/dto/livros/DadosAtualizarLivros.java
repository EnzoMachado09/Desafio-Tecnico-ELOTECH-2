package biblioteca.gestao.api.dto.livros;

import java.time.LocalDate;


import jakarta.validation.constraints.NotNull;

// DTO para atualizar os dados de um livro
public record DadosAtualizarLivros(
                @NotNull Long id,
                String titulo,
                String autor,
                String isbn,
                LocalDate dataPublicacao,
                String categoria) {

}
