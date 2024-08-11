package biblioteca.gestao.api.domain.validacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

// Repository para a entidade Validacao
public interface ValidacaoRepository extends JpaRepository <Validacao, Long> {

    // Busca usuario por login
    UserDetails findByLogin(String login); 

}
