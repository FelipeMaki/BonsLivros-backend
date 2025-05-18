package br.com.bonslivros.BonsLivros.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "livro")
public class Livro implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  
  
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;



  @Column(name = "titulo")
  private String titulo;
  
  @JoinColumn(name = "autor_id")
  @ManyToOne
  private Autor autor;

  @Column(name = "editora")
  private String editora;

  @Column(name = "genero")
  private String genero;

  @Column(name = "data_publicacao")
  private String dataPublicacao;

  public Livro() {
  }

  public Livro(String titulo, Autor autor, String editora, String genero, String dataPublicacao) {
    this.titulo = titulo;
    this.autor = autor;
    this.editora = editora;
    this.genero = genero;
    this.dataPublicacao = dataPublicacao;
  }

  public int getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id != null ? id : 0;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  public String getEditora() {
    return editora;
  }

  public void setEditora(String editora) {
    this.editora = editora;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getDataPublicacao() {
    return dataPublicacao;
  }

  public void setDataPublicacao(String dataPublicacao) {
    this.dataPublicacao = dataPublicacao;
  }

  @Override
  public String toString() {
    return "Livro: {" +
      "id=" + id +
      ", titulo='" + titulo + '\'' +
      ", autor='" + autor + '\'' +
      ", editora='" + editora + '\'' +
      ", genero='" + genero + '\'' +
      ", dataPublicacao='" + dataPublicacao + '\'' +
      '}';
  }
}