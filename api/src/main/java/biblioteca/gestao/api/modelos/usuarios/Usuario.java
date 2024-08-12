package biblioteca.gestao.api.modelos.usuarios;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import biblioteca.gestao.api.dto.usuarios.DadosAtualizarUsuarios;
import biblioteca.gestao.api.dto.usuarios.DadosUsuarios;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entidade que representa um usuario
@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuario {

    // Construtor
    public Usuario(DadosUsuarios dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.data_cadastro = dados.data_cadastro();
        this.telefone = dados.telefone();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private LocalDate data_cadastro;
    private String telefone;
    @JsonIgnore
    private boolean ativo;

    // Metodo para atualizar os dados de um usuario
    public void atualizarUsuario(@Valid DadosAtualizarUsuarios dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

        if (dados.email() != null) {
            this.email = dados.email();
        }

        if (dados.data_cadastro() != null) {
            this.data_cadastro = dados.data_cadastro();
        }

        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
    }

    // Metodo para excluir um usuario
    public void excluir() {
        this.ativo = false;
    }

}
