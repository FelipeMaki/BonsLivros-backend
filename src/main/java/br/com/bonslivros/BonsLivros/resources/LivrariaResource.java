package br.com.bonslivros.BonsLivros.resources;

import br.com.bonslivros.BonsLivros.entities.Autor;
import br.com.bonslivros.BonsLivros.entities.Livro;
import br.com.bonslivros.BonsLivros.entities.LoginDTO;
import br.com.bonslivros.BonsLivros.services.LivrariaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;




@RestController
@RequestMapping("/livraria")
public class LivrariaResource {

    @Autowired
    private LivrariaService service;

    // Buscar livro por ID (você já tem este método)
    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Integer id) {
        Livro obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        List<Livro> livros = service.findAll();
        return ResponseEntity.ok().body(livros);
    }

    // Adicionar novo livro
    @PostMapping
    public ResponseEntity<Livro> insert(@RequestBody Livro livro) {
        livro = service.insert(livro);
        
        // Cria URI para o novo recurso
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livro.getId())
                .toUri();
   
        return ResponseEntity.created(uri).body(livro);
    }

    // adicionar autor
    @PostMapping(value = "/autor")
    public ResponseEntity<Autor> insertAutor(@RequestBody Autor autor) {
        autor = service.insertAutor(autor);
        
        // Cria URI para o novo recurso
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();
   
        return ResponseEntity.created(uri).body(autor);
    }
    
    // Buscar autor
    @GetMapping(value = "/autor")
    public ResponseEntity<List<Autor>> findAllAutores() {
        List<Autor> autores = service.findAllAutores();
        return ResponseEntity.ok().body(autores);
    }

    // Atualizar autor por ID
    @PutMapping("/autor/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Integer id, @RequestBody Autor autor) {
        autor.setId(id); // Garante que o ID é o correto
        Autor autorAtualizado = service.updateAutor(autor);
        return ResponseEntity.ok().body(autorAtualizado);
    }

    // Excluir autor
    @DeleteMapping(value = "/autor/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Integer id) {
        service.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }
    

    // Atualizar livro existente
    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> update(@PathVariable Integer id, @RequestBody Livro livro) {
        livro.setId(id); // Garante que o ID é o correto
        Livro livroAtualizado = service.update(livro);
        return ResponseEntity.ok().body(livroAtualizado);
    }

    // Excluir livro
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar livros por título (pesquisa parcial)
    @GetMapping(value = "/titulo/{titulo}")
    public ResponseEntity<List<Livro>> findByTitulo(@PathVariable String titulo) {
        List<Livro> livros = service.findByTituloContaining(titulo);
        return ResponseEntity.ok().body(livros);
    }

    // Buscar livros por autor
    @GetMapping(value = "/autor/{autorId}")
    public ResponseEntity<List<Livro>> findByAutor(@PathVariable Integer autorId) {
        List<Livro> livros = service.findByAutorId(autorId);
        return ResponseEntity.ok().body(livros);
    }

    @PostMapping("/login")
    public ResponseEntity<Autor> login(@RequestBody LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String senha = loginDTO.getSenha();

        Autor user = service.login(email, senha);
        return ResponseEntity.ok().body(user);
    }
    
}