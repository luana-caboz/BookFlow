package com.bookflow.bookflow_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Financeiro calcularReceita(LocalDate dataInicio, LocalDate dataFim) {
        List<Venda> vendas = vendaRepository.findAll();

        double receitaTotal = vendas.stream()
                .filter(venda -> !venda.getDataVenda().isBefore(dataInicio) && !venda.getDataVenda().isAfter(dataFim))
                .mapToDouble(Venda::getTotal)
                .sum();

        Financeiro financeiro = new Financeiro();
        financeiro.setDataInicio(dataInicio);
        financeiro.setDataFim(dataFim);
        financeiro.setReceitaTotal(receitaTotal);

        return financeiroRepository.save(financeiro);
    }

    public List<Financeiro> listarRegistrosFinanceiros() {
        return financeiroRepository.findAll();
    }
}
