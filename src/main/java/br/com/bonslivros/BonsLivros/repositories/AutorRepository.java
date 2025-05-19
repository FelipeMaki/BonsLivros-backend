package br.com.bonslivros.BonsLivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bonslivros.BonsLivros.entities.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    // Metodo de login por email e senha
    Autor findByEmailAndSenha(String email, String senha);
}
