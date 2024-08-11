package biblioteca.gestao.api.domain.usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import biblioteca.gestao.api.domain.usuarios.DadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.Usuario;
import biblioteca.gestao.api.domain.usuarios.UsuarioRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve encontrar Usuario, pois está ativo, por ID")
    void findByIdAndAtivoTrueCase1() {
        // Arrange
        //Cria e salva um Usuario ativo
        Usuario usuario = cadastrarUsuario("Usuario Teste");
        usuario.setAtivo(true);

        // Act
        //Busca o Usuario pelo id e ativo
        Usuario usuarioEncontrado = usuarioRepository.findByIdAndAtivoTrue(usuario.getId());

        // Assert
        //Verifica se o Usuario foi encontrado e se está ativo
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioEncontrado.isAtivo()).isTrue();
    }

    @Test
    @DisplayName("Não deve encontrar usuario, pois está inativo, por ID")
    void findByIdAndAtivoTrueCase2() {
        // Arrange
        // Cria e salva um usuario inativo
        Usuario usuario = cadastrarUsuario("usuario Inativo");
        usuario.setAtivo(false);

        // Act
        // Busca o usuario pelo id e ativo
        Usuario usuarioEncontrado = usuarioRepository.findByIdAndAtivoTrue(usuario.getId());

        // Assert
        // Verifica se o usuario não foi encontrado, pois está inativo
        assertThat(usuarioEncontrado).isNull();
    }

    @Test
    @DisplayName("Não deve encontrar usuario, pois ID não existe")
    void findByIdAndAtivoTrueCase3() {
        // Act
        // Busca por um id que não existe no banco de dados
        Usuario usuarioEncontrado = usuarioRepository.findByIdAndAtivoTrue(999L);

        // Assert
        // Verifica se o usuario não foi encontrado
        assertThat(usuarioEncontrado).isNull();
    }




    private Usuario cadastrarUsuario(String nome) {
        Usuario usuario = new Usuario(dadosUsuarios(nome));
        em.persist(usuario);
        return usuario;
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