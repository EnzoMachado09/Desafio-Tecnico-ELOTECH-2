package biblioteca.gestao.api.dto.emprestimos;

import java.time.LocalDate;

import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;

// DTO para detalhar um emprestimo
public record DadosDetalhadosEmprestimo(
        Long id,
        Long usuarioId,
        Long livroId,
        String nomeUsuario,
        String tituloLivro,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao) {

    public DadosDetalhadosEmprestimo(Emprestimo emprestimo) {
        this(
                emprestimo.getId(),
                emprestimo.getUsuario().getId(),
                emprestimo.getLivro().getId(),
                emprestimo.getUsuario().getNome(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao());
    }

}
