package biblioteca.gestao.api.domain.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT l FROM Usuario l WHERE l.id = :id AND l.ativo = true")
    Usuario findByIdAndAtivoTrue(@Param("id") Long id);

}
