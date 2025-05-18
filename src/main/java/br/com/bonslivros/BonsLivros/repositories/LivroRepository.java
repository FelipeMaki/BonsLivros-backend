package br.com.bonslivros.BonsLivros.repositories;

import br.com.bonslivros.BonsLivros.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    
    // Buscar livros por título (case-insensitive, partial match)
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar livros por autor
    List<Livro> findByAutorId(Integer autorId);
}