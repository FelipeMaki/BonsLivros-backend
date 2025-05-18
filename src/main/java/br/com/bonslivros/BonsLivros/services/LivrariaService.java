package br.com.bonslivros.BonsLivros.services;

import br.com.bonslivros.BonsLivros.entities.Livro;
import br.com.bonslivros.BonsLivros.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivrariaService {

    @Autowired
    private LivroRepository repository;

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
        // Garante que é um novo livro (ID nulo)
        livro.setId(null);
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
}