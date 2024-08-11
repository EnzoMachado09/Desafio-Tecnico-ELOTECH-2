package biblioteca.gestao.api.domain.usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import biblioteca.gestao.api.domain.usuarios.DadosAtualizarUsuarios;
import biblioteca.gestao.api.domain.usuarios.DadosDetalhadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.DadosUsuarios;
import biblioteca.gestao.api.domain.usuarios.Usuario;
import biblioteca.gestao.api.domain.usuarios.UsuarioRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosUsuarios> dadosUsuariosJson;

    @Autowired
    private JacksonTester<DadosDetalhadosUsuarios> dadosDetalhadosUsuariosJson;

    @Autowired
    private JacksonTester<DadosAtualizarUsuarios> dadosAtualizarUsuariosJson;

    @MockBean
    private UsuarioRepository repository;

    @Test
    @DisplayName("Deve devolver o codigo 400 ao tentar cadastrar um usuario com dados invalidos")
    @WithMockUser // Simula um usuário autenticado
    void cadastrarUsuarioCase1() throws Exception {
        var response = mvc.perform(post("/usuarios")) // Envia uma requisição POST para /usuarios usando o MockMvc
                .andReturn().getResponse(); // Captura a resposta da requisição

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // Verifica se o status da resposta é 400
    }

    @Test
    @DisplayName("Deve devolver o codigo 200 ao tentar cadastrar um usuario com dados validos")
    @WithMockUser // Simula um usuário autenticado
    void cadastrarUsuarioCase2() throws Exception {
        var data = LocalDate.now(); // Cria uma data atual

        var response = mvc // Envia uma requisição POST para /usuarios usando o MockMvc
                .perform(
                        post("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como JSON
                                                                         
                                .content(dadosUsuariosJson.write(
                                        new DadosUsuarios("Usuario Teste", "teste@hotmail.com", data, "123")).getJson()) 
                                        // Converte os dados do usuário para JSON
                                                                                                                         

                )
                .andReturn().getResponse(); // Captura a resposta da requisição

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value()); // Verifica se o status da resposta é 201
                                                                                

        var jsonEsperado = dadosDetalhadosUsuariosJson.write( // Escreve os dados do usuário esperados
                new DadosDetalhadosUsuarios(null, "Usuario Teste", "teste@hotmail.com", data, "123")).getJson(); 
                // Converte os dados esperados para JSON
                                                                                                                

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se a resposta contém os dados esperados
                                                                           
    }

    @Test
    @DisplayName("Deve devolver o código 400 ao tentar atualizar um usuário com dados inválidos")
    @WithMockUser // Simula um usuário autenticado
    void atualizarUsuarioCase1() throws Exception {
        var response = mvc.perform(put("/usuarios")) // Envia uma requisição PUT para /usuarios usando o MockMvc
                .andReturn().getResponse(); // Captura a resposta da requisição

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // Verifica se o status da resposta é 400
                                                                                    
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao tentar atualizar um usuário com dados válidos")
    @WithMockUser // Simula um usuário autenticado
    void atualizarUsuarioCase2() throws Exception {
        var data = LocalDate.now(); // Cria uma data atual

        // Simulação de usuário já existente no banco de dados
        Usuario usuarioExistente = new Usuario(1L, "Usuario Teste", "teste@hotmail.com", data, "123", true);
        when(repository.getReferenceById(1L)).thenReturn(usuarioExistente);

        // Dados do usuário atualizado
        var dadosAtualizar = new DadosAtualizarUsuarios(1L, "Usuario Atualizado", "atualizado@hotmail.com", data,
                "456");

        var response = mvc // Envia uma requisição PUT para /usuarios usando o MockMvc
                .perform(
                        put("/usuarios")
                                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da requisição como
                                                                         // JSON
                                .content(dadosAtualizarUsuariosJson.write(dadosAtualizar).getJson()) // Converte os dados do usuário atualizado para JSON
                                                                                                   
                                                                                                     
                )
                .andReturn().getResponse(); // Captura a resposta da requisição

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        var jsonEsperado = dadosDetalhadosUsuariosJson.write( // Escreve os dados esperados do usuário atualizado
                new DadosDetalhadosUsuarios(1L, "Usuario Atualizado", "atualizado@hotmail.com", data, "456")).getJson(); // Converte
                                                                                                                         // os
                                                                                                                         // dados
                                                                                                                         // esperados
                                                                                                                         // para
                                                                                                                         // JSON

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se a resposta contém os dados
                                                                           // esperados
    }

    @Test
    @DisplayName("Deve devolver o código 200 ao tentar listar usuários listar usuários quando não há nenhum cadastrado")
    @WithMockUser // Simula um usuário autenticado
    void listarUsuariosCase1() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data atual

        // Simulação de uma lista de usuários ativos
        var usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);
        var usuario2 = new Usuario(2L, "Usuario Teste 2", "teste2@hotmail.com", data, "456", true);
        var listaUsuarios = List.of(usuario1, usuario2);

        // Simula o retorno do repository como uma lista dentro de um Page
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(new PageImpl<>(listaUsuarios));

        // Act
        var response = mvc.perform(get("/usuarios") // Envia uma requisição GET para /usuarios usando o MockMvc
                .param("page", "0") // Define o parâmetro de página como 0
                .param("size", "10") // Define o parâmetro de tamanho como 10
                .param("sort", "nome")) // Define o parâmetro de ordenação como nome
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

        // Converta a lista de usuários para JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Adiciona o módulo para suportar LocalDate
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Configura para não usar timestamps

        // Converta a lista de usuários para JSON
        String listaUsuariosJson = mapper.writeValueAsString(listaUsuarios);

        // Extraia o conteúdo JSON da resposta
        String responseContent = response.getContentAsString();
        JsonNode responseJson = mapper.readTree(responseContent).get("content");

        // Converta o conteúdo extraído para JSON e compare com a lista esperada
        String contentJson = mapper.writeValueAsString(responseJson);

        assertThat(contentJson).isEqualTo(listaUsuariosJson); // Verifica se o conteúdo da resposta contém os dados esperados
    }

    @Test
    @DisplayName("Deve devolver o código 204 ao tentar listar usuários quando não há nenhum cadastrado")
    @WithMockUser // Simula um usuário autenticado
    void listarUsuariosCase2() throws Exception {
        // Arrange
        var listaUsuarios = List.<Usuario>of(); // Cria uma lista vazia de usuários

        // Simula o retorno do repository como uma lista dentro de um Page
        when(repository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(new PageImpl<Usuario>(listaUsuarios));

        // Act
        var response = mvc.perform(get("/usuarios") // Envia uma requisição GET para /usuarios usando o MockMvc
                .param("page", "0") // Define o parâmetro de página como 0
                .param("size", "10") // Define o parâmetro de tamanho como 10
                .param("sort", "nome")) // Define o parâmetro de ordenação como nome
                .andReturn().getResponse(); // Captura a resposta da requisição

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()); // Verifica se o status da resposta é 204

        assertThat(response.getContentAsString()).isEmpty(); // Verifica se a resposta está vazia

    }

    @Test
    @DisplayName("Deve devolver o código 204 ao deletar um usuário existente")
    @WithMockUser // Simula um usuário autenticado
    void deletarUsuario() throws Exception {
        // Arrange
        var data = LocalDate.now(); // Cria uma data atual
        var usuario = new Usuario(1L, "Usuario Teste", "teste@hotmail.com", data, "123456789", true);

        when(repository.getReferenceById(usuario.getId())).thenReturn(usuario); // Simula o retorno do repository

        // Act
        var response = mvc.perform(delete("/usuarios/{id}", usuario.getId())) // Envia uma requisição DELETE para /usuarios/{id} usando o MockMvc
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()); // Verifica se o status da resposta é 204
        assertThat(usuario.isAtivo()).isFalse(); // Verifica se o usuário foi marcado como inativo
    }

    @Test
@DisplayName("Deve devolver o código 200 ao buscar um usuario por nome")
@WithMockUser // Simula um usuário autenticado
void buscarUsuarioPorId() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data atual
    var usuario = new Usuario(1L, "Usuario Teste", "teste@hotmail.com", data, "123456789", true);

    when(repository.findByIdAndAtivoTrue(usuario.getId())).thenReturn(usuario);

    // Act
    var response = mvc.perform(get("/usuarios/id/{id}", usuario.getId()))
                      .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

    // Converta o usuario para JSON
    var jsonEsperado = dadosDetalhadosUsuariosJson.write(new DadosDetalhadosUsuarios(usuario)) // Escreve os dados do usuario
                                                   .getJson(); // Converte os dados do usuario para JSON

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // Verifica se o conteúdo da resposta contém os dados esperados
}

@Test
@DisplayName("Deve devolver o código 200 ao buscar um usuario por nome")
@WithMockUser // Simula um usuário autenticado
void buscarUsuarioPorNome() throws Exception {
    // Arrange
    var data = LocalDate.now(); // Cria uma data atual

        // Simulação de uma lista de usuários ativos
        var usuario1 = new Usuario(1L, "Usuario Teste 1", "teste1@hotmail.com", data, "123", true);
        var usuario2 = new Usuario(2L, "Usuario Teste 2", "teste2@hotmail.com", data, "456", true);
        var listaUsuarios = List.of(usuario1, usuario2);

        // Simula o retorno do repository como uma lista dentro de um Page
        when(repository.findByNomeAndAtivoTrue(eq(usuario1.getNome()), any(Pageable.class))).thenReturn(new PageImpl<>(listaUsuarios));

    // Act
    var response = mvc.perform(get("/usuarios/nome/{nome}", usuario1.getNome()))
                      .andReturn().getResponse();

    // Assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // Verifica se o status da resposta é 200

     // Converta a lista de usuários para JSON
     ObjectMapper mapper = new ObjectMapper();
     mapper.registerModule(new JavaTimeModule()); // Adiciona o módulo para suportar LocalDate
     mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Configura para não usar timestamps

     // Converta a lista de usuários para JSON
     String listaUsuariosJson = mapper.writeValueAsString(listaUsuarios);

     // Extraia o conteúdo JSON da resposta
     String responseContent = response.getContentAsString();
     JsonNode responseJson = mapper.readTree(responseContent).get("content");

     // Converta o conteúdo extraído para JSON e compare com a lista esperada
     String contentJson = mapper.writeValueAsString(responseJson);

     assertThat(contentJson).isEqualTo(listaUsuariosJson); // Verifica se o conteúdo da resposta contém os dados esperados
}

}