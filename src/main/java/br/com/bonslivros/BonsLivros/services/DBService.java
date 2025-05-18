package br.com.bonslivros.BonsLivros.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.bonslivros.BonsLivros.entities.Autor;
import br.com.bonslivros.BonsLivros.entities.Livro;
import br.com.bonslivros.BonsLivros.repositories.AutorRepository;
import br.com.bonslivros.BonsLivros.repositories.LivroRepository;

@Service
@Configuration
public class DBService {
  @Autowired
  private LivroRepository livroRepository;
  @Autowired
  private AutorRepository autorRepository;

  @Bean
  public String implementDB() {
    
    Autor a1 = new Autor("J. R. R. Tolkien", "example@email", "1234", "000.000.000-00");
    Autor a2 = new Autor("J. K. Rowling", "example2@email", "4321", "000.000.000-01");
    
    autorRepository.saveAll(Arrays.asList(a1, a2));
    
    Livro l1 = new Livro("Senhor dos Anéis", a1, "Allen & Unwin", "Fantasia", "29/07/1954");
    Livro l2 = new Livro("Hobbit", a1, "Allen & Unwin", "Fantasia", "21/09/1937");
    Livro l3 = new Livro("Silmarillion", a2, "Allen & Unwin", "Fantasia", "15/09/1977");
    Livro l4 = new Livro("Harry Potter e a Pedra Filosofal", a2, "Bloomsbury", "Fantasia", "26/06/1997");
    Livro l5 = new Livro("Harry Potter e a Câmara Secreta", a2, "Bloomsbury", "Fantasia", "02/07/1998");


    livroRepository.saveAll(Arrays.asList(l1, l2, l3, l4, l5));


    return "";
  }
}
