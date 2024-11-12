package com.bookflow.bookflow_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

      // buscar livros por título
      Optional<Livro> findByTituloContaining(String titulo);

      // Lbuscar livros por autor
      List<Livro> findByAutorContaining(String autor);

      // Busca por preço, pode ser útil para filtragem
      List<Livro> findByPrecoBetween(double precoMin, double precoMax);

      // Busca por data de publicação, pode ser útil para ordenação ou filtragem
      List<Livro> findByDataPublicacaoBefore(LocalDate data);
}
