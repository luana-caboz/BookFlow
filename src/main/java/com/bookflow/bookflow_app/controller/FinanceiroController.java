package com.bookflow.bookflow_app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookflow.bookflow_app.model.Financeiro;
import com.bookflow.bookflow_app.service.FinanceiroService;

@RestController
@RequestMapping("/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    @PostMapping("/calcular-receita")
    public ResponseEntity<Financeiro> calcularReceita(@RequestParam LocalDate dataInicio,
                                                      @RequestParam LocalDate dataFim) {
        Financeiro financeiro = financeiroService.calcularReceita(dataInicio, dataFim);
        return ResponseEntity.ok(financeiro);
    }

    @GetMapping
    public ResponseEntity<List<Financeiro>> listarRegistrosFinanceiros() {
        List<Financeiro> registrosFinanceiros = financeiroService.listarRegistrosFinanceiros();
        return ResponseEntity.ok(registrosFinanceiros);
    }
}
