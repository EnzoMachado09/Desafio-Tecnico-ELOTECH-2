package biblioteca.gestao.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import biblioteca.gestao.api.modelos.validacao.Validacao;

// Repository para a entidade Validacao
public interface ValidacaoRepository extends JpaRepository <Validacao, Long> {

    // Busca usuario por login
    UserDetails findByLogin(String login); 

}
