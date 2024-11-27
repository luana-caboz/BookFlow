package com.bookflow.bookflow_app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/despesas")
    public ResponseEntity<Financeiro> criarDespesa(@RequestBody Financeiro despesa) {
        Financeiro novaDespesa = financeiroService.criarDespesa(despesa);
        return ResponseEntity.ok(novaDespesa);
    }

    @GetMapping
    public ResponseEntity<List<Financeiro>> listarRegistrosFinanceiros() {
        List<Financeiro> registrosFinanceiros = financeiroService.listarRegistrosFinanceiros();
        return ResponseEntity.ok(registrosFinanceiros);
    }

    @GetMapping("/despesas/filtrar")
    public ResponseEntity<List<Financeiro>> listarDespesasFiltradas(
            @RequestParam("dataInicio") String dataInicio,
            @RequestParam("dataFim") String dataFim) {
        List<Financeiro> despesas = financeiroService.listarDespesasFiltradas(
                LocalDate.parse(dataInicio), LocalDate.parse(dataFim));
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/despesas/{id}")
    public ResponseEntity<Financeiro> buscarDespesaPorId(@PathVariable int id) {
        try {
            Financeiro despesa = financeiroService.buscarDespesaPorId(id);
            return ResponseEntity.ok(despesa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/despesas/{id}")
    public ResponseEntity<Financeiro> atualizarDespesa(@PathVariable int id, @RequestBody Financeiro despesaAtualizada) {
        try {
            Financeiro despesa = financeiroService.atualizarDespesa(id, despesaAtualizada);
            return ResponseEntity.ok(despesa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/calcular-receita")
public ResponseEntity<Double> calcularReceita(@RequestBody Map<String, String> datas) {
    // Extrair as datas do JSON
    LocalDate dataInicio = LocalDate.parse(datas.get("dataInicio"));
    LocalDate dataFim = LocalDate.parse(datas.get("dataFim"));

    // Calcular a receita
    double receita = financeiroService.calcularReceita(dataInicio, dataFim);
    return ResponseEntity.ok(receita);
}


     @DeleteMapping("/despesas/{id}")
    public ResponseEntity<Void> excluirDespesa(@PathVariable int id) {
        financeiroService.excluirDespesa(id);
        return ResponseEntity.noContent().build();
    }

}   
