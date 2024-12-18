package com.bookflow.bookflow_app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String titulo;
    private String autor;
    private String categoria;
    private double preco;
    private LocalDate dataPublicacao;
    private int quantidadeDisponivel;

    public Livro(){

    }

    public Livro(String titulo, String autor, String categoria, int preco, LocalDate dataPublicacao, int quantidadeDisponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.preco = preco;
        this.dataPublicacao = dataPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    @Override
    public String toString() {
        return "Livros [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", categoria=" + categoria
                + ", dataPublicacao=" + dataPublicacao + ", preco " +preco +"]";
    }

}
