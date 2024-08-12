package biblioteca.gestao.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import biblioteca.gestao.api.domain.validacao.ValidacaoRepository;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    // Injeção de dependências
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ValidacaoRepository repository;

    // Filtro para interceptar as requisições e validar o token JWT
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Recupera o token JWT do cabeçalho da requisição

        if (tokenJWT != null) { // Recupera o login do usuário autenticado no token JWT se o token não for nulo
            var subject = tokenService.getSubject(tokenJWT); // Recupera o login do usuário autenticado no token JWT
            var usuario = repository.findByLogin(subject); // Busca o usuário autenticado no banco de dados

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Autentica o usuário 
            SecurityContextHolder.getContext().setAuthentication(authentication); // Define o usuário autenticado
        } 

        // Valida o token JWT
        filterChain.doFilter(request, response);
    }

    // Recupera o token JWT do cabeçalho da requisição
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); // Recupera o cabeçalho Authorization
        if (authorizationHeader != null) { // Se o cabeçalho Authorization não for nulo
                return authorizationHeader.replace("Bearer ", ""); // Retorna o token JWT sem o prefixo Bearer 
        }
    
        return null; // Retorna nulo se o cabeçalho Authorization for nulo
    } 

}