package biblioteca.gestao.api.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import biblioteca.gestao.api.dto.livros.DadosAtualizarLivros;
import biblioteca.gestao.api.dto.livros.DadosDetalhadosLivros;
import biblioteca.gestao.api.dto.livros.DadosLivros;
import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.repository.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class LivrosService {

    @Autowired
    private LivroRepository repository;

    @Transactional
    public DadosDetalhadosLivros cadastrarLivro(DadosLivros dados, UriComponentsBuilder uriBuilder) {
        Livro livro = new Livro(dados);
        repository.save(livro);
        return new DadosDetalhadosLivros(livro);
    }

    public Page<Livro> listarLivro(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao);
    }

    @Transactional
    public DadosDetalhadosLivros atualizarLivro(DadosAtualizarLivros dados) {
        Livro livro = repository.getReferenceById(dados.id());
        livro.atualizarLivro(dados);
        repository.save(livro);
        return new DadosDetalhadosLivros(livro);
    }

    @Transactional
    public void deletarLivro(Long id) {
        Livro livro = repository.getReferenceById(id);
        livro.excluir();
        repository.save(livro);
    }

    public DadosDetalhadosLivros buscarLivroPorId(Long id) {
        Livro livro = repository.findByIdAndAtivoTrue(id);
        return new DadosDetalhadosLivros(livro);
    }

    public Page<DadosDetalhadosLivros> buscarLivroPorTitulo(String titulo, Pageable paginacao) {
        Page<Livro> livros = repository.findByTituloAndAtivoTrue(titulo, paginacao);
        return livros.map(DadosDetalhadosLivros::new);
    }
}
