package biblioteca.gestao.api.servicos;


import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.repository.EmprestimoRepository;
import biblioteca.gestao.api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecomendacaoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> recomendarLivros(Long usuarioId) {
        // Busca as categorias dos livros que o usuário já emprestou
        List<String> categorias = emprestimoRepository.findDistinctCategoriasByUsuarioId(usuarioId);
        
        // Se o usuário não tiver livros emprestados, não há categorias a recomendar
        if (categorias.isEmpty()) {
            return List.of();
        }

        // Busca os livros disponíveis para recomendação com base nas categorias do usuário
        return livroRepository.findLivrosParaRecomendacao(categorias, usuarioId);
    }
}

