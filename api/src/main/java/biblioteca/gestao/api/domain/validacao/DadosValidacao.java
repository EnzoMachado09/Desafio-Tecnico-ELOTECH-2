package biblioteca.gestao.api.domain.validacao;

// DTO para validação de dados de login
public record DadosValidacao (
        String login,
        String senha
) {

}
