package biblioteca.gestao.api.domain.validacao;

import jakarta.persistence.Table;

import java.util.List;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entidade que representa um usuario
@Table(name = "validacao")
@Entity(name = "Validacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Validacao implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    // Definindo que o usuario tem o papel de "ROLE_USER"
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Definindo a senha do usuario
    @Override
    public String getPassword() {
        return senha;
    }

    // Definindo o login do usuario
    @Override
    public String getUsername() {
        return login;
    }

    // Definindo que o usuario nao expira
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Definindo que o usuario nao esta bloqueado
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Definindo que as credenciais do usuario nao expiram
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Definindo que o usuario esta habilitado
    @Override
    public boolean isEnabled() {
        return true;
    }

}
