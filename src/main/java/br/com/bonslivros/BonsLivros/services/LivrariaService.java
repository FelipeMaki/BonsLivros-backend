package br.com.bonslivros.BonsLivros.services;

import br.com.bonslivros.BonsLivros.entities.Autor;
import br.com.bonslivros.BonsLivros.entities.Livro;
import br.com.bonslivros.BonsLivros.repositories.AutorRepository;
import br.com.bonslivros.BonsLivros.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivrariaService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

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
        Autor autor = livro.getAutor();

        if (autor.getId() != null) {
            Optional<Autor> autorExistente = autorRepository.findById(autor.getId());
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor.setId(null); // anula o ID pra forçar um novo insert
                String cpf = autor.getCpf();
                if (validarcpf.validarCpf(cpf) == false) {
                        throw new RuntimeException("CPF inválido: " + cpf);
                }
                autor = autorRepository.save(autor);
            }
        } else {
            autor = autorRepository.save(autor);
        }

        livro.setAutor(autor);

        return repository.save(livro);
    }

    // Atualizar existente
    public Livro update(Livro livro) {
        // Verifica se existe
        findById(livro.getId());
        return repository.save(livro);
    }

    // Excluir
    public void delete(Integer id) {
        // Verifica se existe
        findById(id);
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
        String cpf = autor.getCpf();
        if (validarcpf.validarCpf(cpf) == false) {
            throw new RuntimeException("CPF inválido: " + cpf);
        }
        return autorRepository.save(autor);
    }
    // lsitar todos os autores
    public List<Autor> findAllAutores() {
        return autorRepository.findAll();
    }

    // Atualizar autor existente
    public Autor updateAutor(Autor autor) {
        // Verifica se existe
        findById(autor.getId());
        return autorRepository.save(autor);
    }

    // Excluir autor
    public void deleteAutor(Integer id) {
        // Verifica se existe
        findById(id);
        autorRepository.deleteById(id);
    }

    // login autor
    public Autor login(String email, String senha) {
        Autor autor = autorRepository.findByEmailAndSenha(email, senha);

        if (autor == null) {
            throw new RuntimeException("Email ou senha inválidos");
        }
        
        return autor;
    }
}