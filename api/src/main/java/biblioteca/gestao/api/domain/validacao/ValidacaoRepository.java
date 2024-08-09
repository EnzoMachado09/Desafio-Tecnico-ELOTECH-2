package biblioteca.gestao.api.domain.validacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ValidacaoRepository extends JpaRepository <Validacao, Long> {

    UserDetails findByLogin(String login); 

}
