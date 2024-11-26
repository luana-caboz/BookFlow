package com.bookflow.bookflow_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

      // buscar livros por id
      Optional<Livro> findById(int id);

      // buscar livros por título
      Optional<Livro> findByTitulo(String titulo);

      // Lbuscar livros por autor
      List<Livro> findByAutor(String autor);

      // Busca por preço
      List<Livro> findByPrecoBetween(double precoMin, double precoMax);

      // Busca por data de publicação
      List<Livro> findByDataPublicacao(LocalDate data);
}
