package com.bookflow.bookflow_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Financeiro;

public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {
}
