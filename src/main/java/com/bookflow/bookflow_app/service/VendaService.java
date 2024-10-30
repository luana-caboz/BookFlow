package com.bookflow.bookflow_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.repository.VendaRepository;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public Venda criarVenda(Venda venda) {
        calcularTotalVenda(venda);
        return vendaRepository.save(venda);
    }

    private void calcularTotalVenda(Venda venda) {
        double total = venda.getLivros().stream()
                            .mapToDouble(livro -> livro.getPreco())
                            .sum();
        venda.setTotal(total);
    }

    public List<Venda> listarTodasAsVendas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarVendaPorId(Long id) {
        return vendaRepository.findById(id);
    }
}
