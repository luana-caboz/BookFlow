package com.bookflow.bookflow_app.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Financeiro;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Integer> {

    Financeiro findByDataInicioAndDataFim(LocalDate dataVenda, LocalDate dataVenda2);
}
