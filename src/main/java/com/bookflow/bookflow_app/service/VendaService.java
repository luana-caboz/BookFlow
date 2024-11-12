package com.bookflow.bookflow_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.model.VendaItem;
import com.bookflow.bookflow_app.repository.VendaRepository;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private EstoqueService estoqueService;

    public Venda criarVenda(Venda venda) {
        double total = 0.0;

        for (VendaItem item : venda.getItens()) {
            Livro livro = item.getLivro();

            if (!estoqueService.verificarDisponibilidade(livro, item.getQuantidade())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para o livro " + livro.getTitulo());
            }

            item.calcularSubTotal();
            total += item.getSubTotal();
            estoqueService.ajustarEstoque(livro, -item.getQuantidade());
        }

        venda.setTotal(total);
        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodasAsVendas() {
        return vendaRepository.findAll();
    }
    
    public Optional<Venda> buscarVendaPorId(int id) {
        return vendaRepository.findById(id);
    }

    public Venda atualizarVenda(int vendaId, Venda novaVenda) {
        Venda vendaExistente = vendaRepository.findById(vendaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada"));

        for (VendaItem item : vendaExistente.getItens()) {
            Livro livro = item.getLivro();
            estoqueService.ajustarEstoque(livro, item.getQuantidade());
        }

        double total = 0.0;

        for (VendaItem item : novaVenda.getItens()) {
            Livro livro = item.getLivro();

            if (!estoqueService.verificarDisponibilidade(livro, item.getQuantidade())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Estoque insuficiente para o livro " + livro.getTitulo());
            }

            item.calcularSubTotal();
            total += item.getSubTotal();
            estoqueService.ajustarEstoque(livro, -item.getQuantidade());
        }

        vendaExistente.setItens(novaVenda.getItens());
        vendaExistente.setTotal(total);
        vendaExistente.setNomeCliente(novaVenda.getNomeCliente());
        vendaExistente.setCpfCliente(novaVenda.getCpfCliente());

        return vendaRepository.save(vendaExistente);
    }


    public void cancelarVenda(int vendaId) {
        Venda venda = vendaRepository.findById(vendaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada"));

        // Reestabelece o estoque para cada item da venda cancelada
        for (VendaItem item : venda.getItens()) {
            Livro livro = item.getLivro();
            estoqueService.ajustarEstoque(livro, item.getQuantidade());
        }

        vendaRepository.deleteById(vendaId); 
    }


}

