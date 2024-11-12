package com.bookflow.bookflow_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Estoque;
import com.bookflow.bookflow_app.model.Livro;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer>{

    Estoque findByLivro(Livro livro);

    int getQuantidadeByLivroId(int id);

}
