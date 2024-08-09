package biblioteca.gestao.api.domain.livros;

public record DadosDetalhadosLivros(
        Long id,
        String titulo,
        String autor,
        String isbn,
        String data_publicacao,
        Categoria categoria) {
    public DadosDetalhadosLivros(Livro livro) {
        this(livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getData_publicacao(),
                livro.getCategoria());
    }

}
