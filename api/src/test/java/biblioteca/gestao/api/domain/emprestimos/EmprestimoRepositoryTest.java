package biblioteca.gestao.api.domain.emprestimos;

import biblioteca.gestao.api.dto.livros.DadosLivros;
import biblioteca.gestao.api.dto.usuarios.DadosUsuarios;
import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;
import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.modelos.usuarios.Usuario;
import biblioteca.gestao.api.repository.EmprestimoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class EmprestimoRepositoryTest {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
@DisplayName("Deve encontrar categorias distintas emprestadas por usuário")
@WithMockUser // Simula um usuário autenticado
void findDistinctCategoriasByUsuarioIdCase1() {
    // Arrange: Cria e salva alguns livros
    var data = LocalDate.now();
    Livro livro1 = cadastrarLivro("Livro Teste 1", "Categoria 1");
    Livro livro2 = cadastrarLivro("Livro Teste 2", "Categoria 1");
    Livro livro3 = cadastrarLivro("Livro Teste 3", "Categoria 2");

    // Cria e salva um usuário
    Usuario usuario = cadastrarUsuario("Usuário Teste");

    // Cria e salva empréstimos para os livros
    cadastrarEmprestimo(livro1, usuario, data);
    cadastrarEmprestimo(livro2, usuario, data);
    cadastrarEmprestimo(livro3, usuario, data);

    // Act: Busca categorias distintas emprestadas pelo usuário
    List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario.getId());

    // Assert: Verifica se as categorias distintas foram encontradas corretamente
    assertThat(categorias).containsExactlyInAnyOrder("Categoria 1", "Categoria 2");
}


    @Test
    @DisplayName("Não deve encontrar categorias emprestadas quando o usuário não tiver empréstimos")
    @WithMockUser // Simula um usuário autenticado
    void findDistinctCategoriasByUsuarioIdCase2() {
        // Arrange
        // Cria e salva um usuário
        Usuario usuario = cadastrarUsuario("Usuário Teste");

        // Act: Busca categorias distintas emprestadas pelo usuário
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario.getId());

        // Assert: Verifica se a lista de categorias está vazia
        assertThat(categorias).isEmpty();
    }

    @Test
    @DisplayName("Não deve encontrar categorias emprestadas quando o usuário não existe")
    @WithMockUser // Simula um usuário autenticado
    void findDistinctCategoriasByUsuarioIdCase3() {
        // Act: Busca categorias distintas emprestadas por um id de usuário que não
        // existe
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(999L);

        // Assert: Verifica se a lista de categorias está vazia
        assertThat(categorias).isEmpty();
    }

    private Emprestimo cadastrarEmprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {
        // Cria o objeto Emprestimo com os dados fornecidos
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo);
        // Persiste o empréstimo no banco de dados
        em.persist(emprestimo);
        // Retorna o empréstimo persistido
        return emprestimo;
    }

    private Livro cadastrarLivro(String titulo, String categoria) {
        Livro livro = new Livro(dadosLivros(titulo, categoria));
        em.persist(livro);
        return livro;
    }

    private Usuario cadastrarUsuario(String nome) {
        Usuario usuario = new Usuario(dadosUsuarios(nome));
        em.persist(usuario);
        return usuario;
    }

    private DadosLivros dadosLivros(String titulo, String categoria) {
        return new DadosLivros(
                titulo,
                "Autor Teste", // Valor padrão para autor
                "ISBNTeste", // Valor padrão para ISBN
                null, // Valor padrão para data de publicação
                categoria);
    }

    private DadosUsuarios dadosUsuarios(String nome) {
        return new DadosUsuarios(
                nome,
                "teste@hotmail.com", // Valor padrão para e-mail
                null, // Valor padrão para data de nascimento
                "998960004" // Valor padrão para telefone
        );
    }
}
