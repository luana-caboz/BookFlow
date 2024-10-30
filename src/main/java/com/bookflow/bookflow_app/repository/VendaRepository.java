package com.bookflow.bookflow_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
