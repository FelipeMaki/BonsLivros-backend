package br.com.bonslivros.BonsLivros.services;

import br.com.bonslivros.BonsLivros.entities.Autor;
import br.com.bonslivros.BonsLivros.entities.Livro;
// Removido import não utilizado de LoginDTO se não for usado aqui
import br.com.bonslivros.BonsLivros.repositories.AutorRepository;
import br.com.bonslivros.BonsLivros.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
// Removido import não utilizado de HttpStatus se não for usado aqui
import org.springframework.stereotype.Service;
// Removido import não utilizado de User se não for usado aqui

import java.util.List;
import java.util.Optional;

@Service
public class LivrariaService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    // Supondo que validarcpf é uma classe com um método estático
    // ou uma instância injetada se for um @Service.
    // Para este exemplo, vou assumir que é estático ou você tem uma forma de chamá-lo.
    // Ex: @Autowired private ValidarCpfService validarCpfService;

    // Buscar por ID
    public Livro findById(Integer id) {
        Optional<Livro> obj = repository.findById(id);
        return obj.orElseThrow(() -> new RuntimeException("Livro não encontrado! ID: " + id));
    }

    // Listar todos
    public List<Livro> findAll() {
        return repository.findAll();
    }

    // Inserir novo
    public Livro insert(Livro livro) {
        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            throw new IllegalArgumentException("Autor e ID do Autor são obrigatórios para cadastrar um livro.");
        }

        Integer autorIdRecebido = livro.getAutor().getId();

        // Busca o autor completo no banco de dados usando o ID fornecido.
        Autor autorParaAssociar = autorRepository.findById(autorIdRecebido)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + autorIdRecebido +
                        ". Não é possível cadastrar o livro com um autor inexistente."));

        // Neste ponto, autorParaAssociar é uma entidade gerenciada e completa do banco de dados.
        // O CPF (e outros campos) de autorParaAssociar serão os que estão no banco.
        // Não há necessidade de validar o CPF aqui novamente se ele já foi validado ao criar/atualizar o autor.
        // Se ainda assim quiser validar:
        /*
        if (autorParaAssociar.getCpf() == null || !validarcpf.validarCpf(autorParaAssociar.getCpf())) {
             throw new RuntimeException("CPF do autor associado é inválido ou nulo. Autor ID: " + autorParaAssociar.getId());
        }
        */

        livro.setAutor(autorParaAssociar); // Associa o autor encontrado e completo ao livro.
        return repository.save(livro);    // Salva o livro.
    }


    // Atualizar existente
    public Livro update(Livro livro) {
        Livro livroExistente = findById(livro.getId()); // Verifica se o livro existe

        // Lógica similar para o autor, se o autor puder ser alterado na atualização do livro
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            Autor autorParaAssociar = autorRepository.findById(livro.getAutor().getId())
                    .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + livro.getAutor().getId() +
                            " para atualização do livro."));
            livroExistente.setAutor(autorParaAssociar);
        } else if (livro.getAutor() != null) { // Se um objeto autor foi enviado, mas sem ID (implica novo autor?)
            throw new IllegalArgumentException("Para associar um autor existente, o ID do autor é necessário. Para criar um novo autor, use o endpoint de criação de autor.");
        }


        // Atualiza outros campos do livroExistente com os valores de 'livro'
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setGenero(livro.getGenero());
        livroExistente.setEditora(livro.getEditora());
        livroExistente.setDataPublicacao(livro.getDataPublicacao());
        // ... quaisquer outros campos ...

        return repository.save(livroExistente);
    }

    // Excluir
    public void delete(Integer id) {
        findById(id); // Verifica se existe
        repository.deleteById(id);
    }

    // Buscar por título
    public List<Livro> findByTituloContaining(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    // Buscar por autor
    public List<Livro> findByAutorId(Integer autorId) {
        return repository.findByAutorId(autorId);
    }

    // Inserir novo autor
    public Autor insertAutor(Autor autor) {
        // A validação do CPF deve ocorrer aqui, antes de salvar o Autor.
        // E validarcpf.validarCpf deve tratar CPF nulo.
        if (autor.getCpf() == null) {
            throw new IllegalArgumentException("CPF é obrigatório para cadastrar um novo autor.");
        }
        if (!validarcpf.validarCpf(autor.getCpf())) { // Assumindo que validarCpf agora trata null
            throw new RuntimeException("CPF inválido: " + autor.getCpf());
        }
        return autorRepository.save(autor);
    }

    // listar todos os autores
    public List<Autor> findAllAutores() {
        return autorRepository.findAll();
    }

    // Atualizar autor existente
    public Autor updateAutor(Autor autorNovosDados) {
        if (autorNovosDados.getId() == null) {
            throw new IllegalArgumentException("ID do Autor é obrigatório para atualização.");
        }
        Autor autorExistente = autorRepository.findById(autorNovosDados.getId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + autorNovosDados.getId()));

        // Valida CPF se foi alterado e não é nulo
        if (autorNovosDados.getCpf() != null && !autorNovosDados.getCpf().equals(autorExistente.getCpf())) {
            if (!validarcpf.validarCpf(autorNovosDados.getCpf())) {
                throw new RuntimeException("CPF inválido para atualização: " + autorNovosDados.getCpf());
            }
            autorExistente.setCpf(autorNovosDados.getCpf());
        }


        // Atualiza outros campos
        autorExistente.setNome(autorNovosDados.getNome());
        autorExistente.setEmail(autorNovosDados.getEmail());
        // Não atualize a senha aqui diretamente a menos que seja a intenção e haja tratamento adequado
        // autorExistente.setSenha(autorNovosDados.getSenha());


        return autorRepository.save(autorExistente);
    }

    // Excluir autor
    public void deleteAutor(Integer id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado com ID: " + id);
        }
        // Adicionar verificação se o autor possui livros associados antes de excluir, se necessário
        List<Livro> livrosDoAutor = repository.findByAutorId(id);
        if (!livrosDoAutor.isEmpty()) {
            throw new RuntimeException("Não é possível excluir o autor ID: " + id + " pois ele possui livros associados.");
        }
        autorRepository.deleteById(id);
    }

    // login autor
    public Autor login(String email, String senha) {
        Autor autor = autorRepository.findByEmailAndSenha(email, senha);

        if (autor == null) {
            // Em vez de RuntimeException, considere uma exceção mais específica ou um status HTTP diferente no Resource.
            throw new RuntimeException("Email ou senha inválidos");
        }
        return autor;
    }
}
