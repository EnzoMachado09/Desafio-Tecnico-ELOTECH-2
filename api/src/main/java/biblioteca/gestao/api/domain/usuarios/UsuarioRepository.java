package biblioteca.gestao.api.domain.usuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repository para a entidade Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca todos os usuarios ativos
    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

    // Busca usuario por id e ativo 
    @Query("SELECT l FROM Usuario l WHERE l.id = :id AND l.ativo = true")
    Usuario findByIdAndAtivoTrue(@Param("id") Long id);

    Page<Usuario> findByNomeAndAtivoTrue(String nome, Pageable pageable);


}
