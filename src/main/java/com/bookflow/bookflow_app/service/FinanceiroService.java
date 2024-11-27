package com.bookflow.bookflow_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookflow.bookflow_app.model.Financeiro;
import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.repository.FinanceiroRepository;
import com.bookflow.bookflow_app.repository.VendaRepository;

@Service
public class FinanceiroService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private FinanceiroRepository financeiroRepository;

    public Financeiro criarDespesa(Financeiro despesa) {
        return financeiroRepository.save(despesa);
    }

    public List<Financeiro> listarRegistrosFinanceiros() {
        return financeiroRepository.findAll();
    }

    public List<Financeiro> listarDespesasFiltradas(LocalDate dataInicio, LocalDate dataFim) {
        return financeiroRepository.findByDataBetween(dataInicio, dataFim);
    }

    public Financeiro buscarDespesaPorId(int id) {
        return financeiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    public Financeiro atualizarDespesa(int id, Financeiro despesaAtualizada) {
        return financeiroRepository.findById(id)
                .map(despesa -> {
                    despesa.setDescricao(despesaAtualizada.getDescricao());
                    despesa.setFormaPagamento(despesaAtualizada.getFormaPagamento());
                    despesa.setStatus(despesaAtualizada.getStatus());
                    despesa.setValor(despesaAtualizada.getValor());
                    despesa.setData(despesaAtualizada.getData());
                    return financeiroRepository.save(despesa);
                })
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    @Transactional
    public double calcularReceita(LocalDate dataInicio, LocalDate dataFim) {

        List<Venda> vendas = vendaRepository.findByDataVendaBetweenAndStatusNot(dataInicio, dataFim, "CANCELADA");
        double totalVendas = vendas.stream().mapToDouble(Venda::getTotal).sum();

        List<Financeiro> despesas = listarDespesasFiltradas(dataInicio, dataFim);
        double totalDespesas = despesas.stream().mapToDouble(Financeiro::getValor).sum();

        return totalVendas - totalDespesas;
    }

    public void excluirDespesa(int id) {
        financeiroRepository.deleteById(id);
    }
}
