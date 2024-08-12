package biblioteca.gestao.api.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import biblioteca.gestao.api.dto.emprestimos.DadosAtualizarEmprestimo;
import biblioteca.gestao.api.dto.emprestimos.DadosDetalhadosEmprestimo;
import biblioteca.gestao.api.dto.emprestimos.DadosEmprestimos;
import biblioteca.gestao.api.modelos.emprestimos.Emprestimo;
import biblioteca.gestao.api.modelos.livros.Livro;
import biblioteca.gestao.api.modelos.usuarios.Usuario;
import biblioteca.gestao.api.repository.EmprestimoRepository;
import biblioteca.gestao.api.repository.LivroRepository;
import biblioteca.gestao.api.repository.UsuarioRepository;

@Service
public class EmprestimosService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public DadosDetalhadosEmprestimo cadastrarEmprestimo(DadosEmprestimos dados) {
        Usuario usuario = usuarioRepository.getReferenceById(dados.usuarioId());
        Livro livro = livroRepository.getReferenceById(dados.livroId());

        // Verifica se o livro já está emprestado
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.findAllByLivroIdAndStatus(livro.getId(), "ATIVO");
        if (!emprestimosAtivos.isEmpty()) {
            throw new RuntimeException("Livro já está emprestado.");
        }

        // Se o livro não estiver emprestado, cria um novo empréstimo
        Emprestimo emprestimo = new Emprestimo(usuario, livro, dados.dataEmprestimo());
        emprestimoRepository.save(emprestimo);

        return new DadosDetalhadosEmprestimo(emprestimo);
    }

    public DadosDetalhadosEmprestimo atualizarEmprestimo(DadosAtualizarEmprestimo dados) {
        Emprestimo emprestimo = emprestimoRepository.getReferenceById(dados.id());
        emprestimo.devolverLivro(dados.dataDevolucao());
        emprestimoRepository.save(emprestimo);

        return new DadosDetalhadosEmprestimo(emprestimo);
    }

    public Emprestimo buscarEmprestimoId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));
    }

    public Page<Emprestimo> listarEmprestimosPorStatus(String status, Pageable paginacao) {
        return emprestimoRepository.findAllByStatus(status, paginacao);
    }
}
