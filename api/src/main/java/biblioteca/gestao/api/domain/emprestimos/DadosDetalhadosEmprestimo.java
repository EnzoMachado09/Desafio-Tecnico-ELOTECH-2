package biblioteca.gestao.api.domain.emprestimos;

public record DadosDetalhadosEmprestimo(
        Long id,
        Long usuarioId,
        Long livroId,
        String nomeUsuario,
        String tituloLivro,
        String dataEmprestimo,
        String dataDevolucao) {

    public DadosDetalhadosEmprestimo(Emprestimo emprestimo) {
        this(
                emprestimo.getId(),
                emprestimo.getUsuario().getId(),
                emprestimo.getLivro().getId(),
                emprestimo.getUsuario().getNome(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getDataEmprestimo().toString(),
                emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao().toString() : null);
    }

}
