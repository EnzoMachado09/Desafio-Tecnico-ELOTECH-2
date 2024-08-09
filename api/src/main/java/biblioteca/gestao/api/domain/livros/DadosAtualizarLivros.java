package biblioteca.gestao.api.domain.livros;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarLivros(
        @NotNull Long id,
        String titulo,
        String autor,
        String isbn,
        String data_publicacao,
        Categoria categoria) {

}
