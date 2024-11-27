package com.bookflow.bookflow_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookflow.bookflow_app.model.Financeiro;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Integer> {

@Query("SELECT f FROM Financeiro f WHERE f.data BETWEEN :dataInicio AND :dataFim")
    List<Financeiro> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

    Optional<Financeiro> findById(Long id);

    void deleteById(Long id);
}
