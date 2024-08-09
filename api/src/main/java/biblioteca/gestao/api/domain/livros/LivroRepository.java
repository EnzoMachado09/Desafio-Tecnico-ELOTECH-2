package biblioteca.gestao.api.domain.livros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Page<Livro> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT l FROM Livro l WHERE l.id = :id AND l.ativo = true")
    Livro findByIdAndAtivoTrue(@Param("id") Long id);

    @Query("SELECT l FROM Livro l WHERE l.categoria IN :categorias AND l.id NOT IN (SELECT e.livro.id FROM Emprestimo e WHERE e.usuario.id = :usuarioId)")
    List<Livro> findLivrosParaRecomendacao(List<String> categorias, Long usuarioId);
}
