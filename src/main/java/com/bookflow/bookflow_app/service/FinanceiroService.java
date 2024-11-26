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

    @Transactional
    public Financeiro calcularReceita(LocalDate dataInicio, LocalDate dataFim) {
        List<Venda> vendas = vendaRepository.findByDataVendaBetweenAndStatusNot(dataInicio, dataFim, "CANCELADA");

        double receitaTotal = vendas.stream()
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

    @Transactional
    public void atualizarReceitaApósCancelamento(Venda vendaCancelada) {
        // Recalcula a receita após o cancelamento de uma venda
        Financeiro financeiro = financeiroRepository.findByDataInicioAndDataFim(
                vendaCancelada.getDataVenda(), vendaCancelada.getDataVenda()); // Pode ser ajustado para mais
                                                                               // flexibilidade

        if (financeiro != null) {
            financeiro.setReceitaTotal(financeiro.getReceitaTotal() - vendaCancelada.getTotal());
            financeiroRepository.save(financeiro);
        }
    }
}
