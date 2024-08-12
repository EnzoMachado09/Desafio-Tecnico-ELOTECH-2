package biblioteca.gestao.api.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.repository.EmprestimoRepository;
import biblioteca.gestao.api.repository.LivroRepository;

@Service
public class RecomendacaoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public RecomendacaoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    public List<Livro> recomendarLivros(Long usuarioId) {
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuarioId);
        if (categorias.isEmpty()) {
            return List.of(); // Retorna lista vazia se não houver categorias
        }

        return livroRepository.findLivrosParaRecomendacao(categorias, usuarioId);
    }
}
