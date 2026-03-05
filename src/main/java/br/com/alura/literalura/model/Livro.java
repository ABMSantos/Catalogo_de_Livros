package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        // Pega apenas o primeiro idioma da lista para simplificar
        this.idioma = dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty() ? dadosLivro.idiomas().get(0) : "Desconhecido";
        this.numeroDownloads = dadosLivro.numeroDownloads();
        this.autor = autor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public Autor getAutor() { return autor; }

    @Override
    public String toString() {
        return "Livro: " + titulo + " | Idioma: " + idioma + " | Downloads: " + numeroDownloads + " | Autor: " + autor.getNome();
    }
}