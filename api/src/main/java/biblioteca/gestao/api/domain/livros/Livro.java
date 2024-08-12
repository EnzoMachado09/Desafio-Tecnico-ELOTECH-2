package biblioteca.gestao.api.domain.livros;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

// Entidade que representa um livro
@Table(name = "livros")
@Entity(name = "Livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Livro {

    // Construtor
    public Livro(DadosLivros dados) {
        this.titulo = dados.titulo();
        this.autor = dados.autor();
        this.isbn = dados.isbn();
        this.dataPublicacao = dados.dataPublicacao();
        this.categoria = dados.categoria();
        this.ativo = true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    @JsonIgnore
    private Boolean ativo;

    private String categoria;

    // Metodo para atualizar os dados de um livro
    public void atualizarLivro(@Valid DadosAtualizarLivros dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }

        if (dados.autor() != null) {
            this.autor = dados.autor();
        }

        if (dados.isbn() != null) {
            this.isbn = dados.isbn();
        }

        if (dados.dataPublicacao() != null) {
            this.dataPublicacao = dados.dataPublicacao();
        }

        if (dados.categoria() != null) {
            this.categoria = dados.categoria();
        }
    }

    // Metodo para excluir um livro
    public void excluir() {
        this.ativo = false;
    }

}
