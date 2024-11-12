package com.bookflow.bookflow_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer>{

    List<Venda> findByDataVendaBetweenAndStatusNot(LocalDate dataInicio, LocalDate dataFim, String status);

}
