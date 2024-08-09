package biblioteca.gestao.api.domain.emprestimos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Page<Emprestimo> findAllByStatus(String status, Pageable pageable);
    List<Emprestimo> findAllByUsuarioId(Long usuarioId);
    List<Emprestimo> findAllByLivroIdAndStatus(Long livroId, String status);
    List<Emprestimo> findAllByStatus(String string);
    
    @Query("SELECT DISTINCT l.categoria FROM Emprestimo e JOIN e.livro l WHERE e.usuario.id = :usuarioId")
    List<String> findDistinctCategoriasByUsuarioId(Long usuarioId);
}
