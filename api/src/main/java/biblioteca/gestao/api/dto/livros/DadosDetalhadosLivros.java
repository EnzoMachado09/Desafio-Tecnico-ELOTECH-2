package biblioteca.gestao.api.dto.livros;

import java.time.LocalDate;

import biblioteca.gestao.api.modelos.livros.Livro;

// DTO para detalhar os dados de um livro 
public record DadosDetalhadosLivros(
        Long id,
        String titulo,
        String autor,
        String isbn,
        LocalDate dataPublicacao,
        String categoria) {
    public DadosDetalhadosLivros(Livro livro) {
        this(livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getDataPublicacao(),
                livro.getCategoria());
    }

}
