package biblioteca.gestao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblioteca.gestao.api.dto.validacao.DadosValidacao;
import biblioteca.gestao.api.infra.security.DadosTokenJWT;
import biblioteca.gestao.api.infra.security.TokenService;
import biblioteca.gestao.api.modelos.validacao.Validacao;
import jakarta.validation.Valid;

// Controller para a validação do login
@RestController
@RequestMapping("/validacao")
public class ValidacaoController {

    // Injeção de dependências
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping // efetuar login no sistema
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosValidacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken); // autentica o login e senha no sistema 
        
        var tokenJWT = tokenService.gerarToken((Validacao)authentication.getPrincipal()); // gera o token JWT para o usuário autenticado

        // retorna o token gerado em um json para o usuário autenticado no sistema 
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
