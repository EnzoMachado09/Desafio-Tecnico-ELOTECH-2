package biblioteca.gestao.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;

import java.util.List;
// Repository para a entidade Emprestimo
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Page<Emprestimo> findAllByStatus(String status, Pageable pageable); // Busca emprestimos por status com paginacao
    List<Emprestimo> findAllByUsuarioId(Long usuarioId); // Busca emprestimos por id de usuario
    List<Emprestimo> findAllByLivroIdAndStatus(Long livroId, String status); // Busca emprestimos por id de livro e status
    List<Emprestimo> findAllByStatus(String string); // Busca emprestimos por status
    
    // Busca categorias distintas por id de usuario
    @Query("SELECT DISTINCT l.categoria FROM Emprestimo e JOIN e.livro l WHERE e.usuario.id = :usuarioId")
    List<String> findDistinctCategoriasByUsuarioId(Long usuarioId);
}
