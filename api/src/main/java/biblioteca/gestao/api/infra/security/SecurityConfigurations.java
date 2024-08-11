package biblioteca.gestao.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Classe de configuração do Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean // Configuração de segurança do Spring Security
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desabilita o CSRF pois a aplicação é stateless e não guarda sessão 
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de sessão como stateless
                .authorizeHttpRequests(req -> { // Configura as autorizações das requisições
                    req.requestMatchers("/validacao").permitAll(); // Permite todas as requisições para o endpoint /validacao
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // Permite todas as requisições para a documentação
                    req.anyRequest().authenticated(); // Todas as outras requisições precisam de autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o meu filtro antes do filtro do spring
                .build();
    }

    @Bean // Configuração de autenticação do Spring Security
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Retorna o gerenciador de autenticação
    };

    @Bean // Configuração de criptografia de senha do Spring Security
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
