package biblioteca.gestao.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import biblioteca.gestao.api.domain.validacao.Validacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Classe que gera o token jwt
@Service
public class TokenService {

    @Value("${api.security.token.secret}") // senha para gerar o token
    private String secret;

    // Gera o token jwt (codigo alterado da documentação da biblioteca auth0)
    public String gerarToken(Validacao validacao) { 
        try { 
            var algoritmo = Algorithm.HMAC256(secret); // senha para gerar o token
            return JWT.create()
                .withIssuer("API Biblioteca") // informaççao para o cabeçalho do token
                .withSubject(validacao.getLogin()) // informações que vão no corpo do token
                .withExpiresAt(dataExpiracao()) // data de expiração do token
                .sign(algoritmo); // assina o token com a senha
        } catch (JWTCreationException exception){ // Erro ao gerar token jwt
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }		
    }

    // Retorna o login do usuário autenticado no token jwt validando o token (codigo alterado da documentação da biblioteca auth0)
    public String getSubject(String tokenJWT) {
    try {
        var algoritmo = Algorithm.HMAC256(secret); // senha do token
        return JWT.require(algoritmo)
                        .withIssuer("API Biblioteca")  // informaççao para o cabeçalho do token
                        .build() // constrói o validador do token
                        .verify(tokenJWT) // verifica o token
                        .getSubject(); // retorna o login do usuário autenticado no token
    } catch (JWTVerificationException exception) {
        throw new RuntimeException("Token JWT inválido ou expirado!"); // Erro ao validar o token jwt
    }
}

    // Retorna a data de expiração do token
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}