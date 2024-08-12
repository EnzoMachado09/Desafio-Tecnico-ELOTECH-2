package biblioteca.gestao.api.dto.googleBooks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// DTO para podermos transformas os dados recebidos no Google Books em um objeto Livro
public class DadosGoogleBooks {
        private String titulo;
        private String autor;
        private String isbn;
        private String dataPublicacao;
        private String categoria;
    
        // Construtor
        public DadosGoogleBooks(String titulo, String autor, String isbn, String dataPublicacao, String categoria) {
            this.titulo = titulo;
            this.autor = autor;
            this.isbn = isbn;
            this.dataPublicacao = dataPublicacao;
            this.categoria = categoria;
        }
    
        
    }
