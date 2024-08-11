package biblioteca.gestao.api.domain.livros;

import biblioteca.gestao.api.domain.emprestimos.Emprestimo;
import biblioteca.gestao.api.domain.emprestimos.EmprestimoRepository;
import biblioteca.gestao.api.domain.usuarios.DadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;  

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve encontrar livro, pois está ativo, por ID")
    void findByIdAndAtivoTrueCase1() {
        // Arrange: Cria e salva um livro ativo
        Livro livro = cadastrarLivro("Livro Teste", "Categoria Teste");
        livro.setAtivo(true);

        // Act: Busca o livro pelo id e ativo
        Livro livroEncontrado = livroRepository.findByIdAndAtivoTrue(livro.getId());

        // Assert: Verifica se o livro foi encontrado e se está ativo
        assertThat(livroEncontrado).isNotNull();
        assertThat(livroEncontrado.getId()).isEqualTo(livro.getId());
        assertThat(livroEncontrado.getAtivo()).isTrue();
    }

    @Test
    @DisplayName("Não deve encontrar livro, pois está inativo, por ID")
    void findByIdAndAtivoTrueCase2() {
        // Arrange: Cria e salva um livro inativo
        Livro livro = cadastrarLivro("Livro Inativo", "Categoria Teste");
        livro.setAtivo(false);

        // Act: Busca o livro pelo id e ativo
        Livro livroEncontrado = livroRepository.findByIdAndAtivoTrue(livro.getId());

        // Assert: Verifica se o livro não foi encontrado, pois está inativo
        assertThat(livroEncontrado).isNull();
    }

    @Test
    @DisplayName("Não deve encontrar livro, pois ID não existe")
    void findByIdAndAtivoTrueCase3() {
        // Act: Busca por um id que não existe no banco de dados
        Livro livroEncontrado = livroRepository.findByIdAndAtivoTrue(999L);

        // Assert: Verifica se o livro não foi encontrado
        assertThat(livroEncontrado).isNull();
    }

    @Test
    @DisplayName("Deve retornar lista de livros para recomendação excluindo os já emprestados pelo usuário")
    void findLivrosParaRecomendacaoCase1() {
        // Arrange
        Livro livro1 = cadastrarLivro("Livro Teste 1", "Categoria 1");
        Livro livro2 = cadastrarLivro("Livro Teste 2", "Categoria 1");
        Livro livro3 = cadastrarLivro("Livro Teste 3", "Categoria 1");

        // Cria e salva um usuário
        Usuario usuario = cadastrarUsuario("Usuário Teste");

        // Cria e salva um empréstimo para o livro1
        cadastrarEmprestimo(livro1, usuario, LocalDate.now());

        // Act
        // Busca as categorias dos livros emprestados pelo usuário
        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao( 
                List.of(livro1.getCategoria(), livro2.getCategoria(), livro3.getCategoria()), 
                usuario.getId());

        // Assert
        //Verifica se apenas o livro da Categoria 2 foi retornado
        assertThat(livrosRecomendados).hasSize(2);
        assertThat(livrosRecomendados.get(0).getTitulo()).isEqualTo(livro2.getTitulo());
        assertThat(livrosRecomendados.get(1).getTitulo()).isEqualTo(livro3.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando usuário já emprestou todos os livros da categoria")
    void findLivrosParaRecomendacaoCase2() {
        // Arrange
        //Cria e salva alguns livros
        Livro livro1 = cadastrarLivro("Livro Teste 1", "Categoria 1");
        Livro livro2 = cadastrarLivro("Livro Teste 2", "Categoria 1");

        // Cria e salva um usuário
        Usuario usuario = cadastrarUsuario("Usuário Teste");

        // Cria e salva empréstimos para todos os livros
        cadastrarEmprestimo(livro1, usuario, LocalDate.now());
        cadastrarEmprestimo(livro2, usuario, LocalDate.now());

        // Busca as categorias dos livros emprestados pelo usuário
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario.getId());

        // Act
        //Busca livros para recomendação excluindo os já emprestados pelo usuário
        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao(categorias,usuario.getId());
                

        // Assert
        //Verifica se a lista de recomendação está vazia
        assertThat(livrosRecomendados).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem livros na categoria emprestada")
    void findLivrosParaRecomendacaoCase3() {
        // Arrange
        //Cria e salva alguns livros
        Livro livro1 = cadastrarLivro("Livro Teste 1", "Categoria 1");
        @SuppressWarnings("unused")
        Livro livro2 = cadastrarLivro("Livro Teste 2", "Categoria 2");

        // Cria e salva um usuário
        Usuario usuario = cadastrarUsuario("Usuário Teste");

        // Cria e salva um empréstimo para o livro da Categoria 1
        cadastrarEmprestimo(livro1, usuario, LocalDate.now());

        // Busca as categorias dos livros emprestados pelo usuário
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario.getId());

        // Act
        //Busca livros para recomendação excluindo os já emprestados pelo usuário
        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao(categorias, usuario.getId());
        // Assert
        // Verifica se a lista de recomendação está vazia pois não há outros livros da mesma categoria
        assertThat(livrosRecomendados).isEmpty();
    }



    @Test
    @DisplayName("Deve retornar lista vazia quando o usuario nao tiver feito emprestimos")
    void findLivrosParaRecomendacaoCase4() {
        // Arrange
        // Cria e salva alguns livros
        Livro livro1 = cadastrarLivro("Livro Teste 1", "Categoria 1");
        @SuppressWarnings("unused")
        Livro livro2 = cadastrarLivro("Livro Teste 2", "Categoria 1");

        // Cria e salva um usuário
        Usuario usuario1 = cadastrarUsuario("Usuário Teste 1");
        Usuario usuario2 = cadastrarUsuario("Usuário Teste 2");


        // Cria e salva um empréstimo para o livro da Categoria 1
        cadastrarEmprestimo(livro1, usuario1, LocalDate.now());

        // Busca as categorias dos livros emprestados pelo usuário
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuario2.getId());

        // Act
        // Busca livros para recomendação excluindo os já emprestados pelo usuário
        List<Livro> livrosRecomendados = livroRepository.findLivrosParaRecomendacao(categorias, usuario2.getId());
        // Assert
        // Verifica se a lista de recomendação está vazia pois não há outros livros da mesma categoria
        assertThat(livrosRecomendados).isEmpty();
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