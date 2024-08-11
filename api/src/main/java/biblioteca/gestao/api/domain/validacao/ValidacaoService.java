package biblioteca.gestao.api.domain.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Serviço para validação de dados de login
@Service
public class ValidacaoService implements UserDetailsService {

    
    @Autowired
    private ValidacaoRepository repository;

    @Override // Busca usuario por login
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
        
    }

}
