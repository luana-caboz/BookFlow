package com.bookflow.bookflow_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Estoque;
import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.repository.EstoqueRepository;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public boolean verificarDisponibilidade(Livro livro, int quantidade) {
        Estoque estoque = estoqueRepository.findById(livro.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado para o livro"));

        return estoque.getQuantidadeDisponivel() >= quantidade;
    }

    public void ajustarEstoque(Livro livro, int quantidade) {
        Estoque estoque = estoqueRepository.findById(livro.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado para o livro"));

        int novaQuantidade = estoque.getQuantidadeDisponivel() + quantidade;

        if (novaQuantidade < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para " + livro.getTitulo());
        }

        estoque.setQuantidadeDisponivel(novaQuantidade);
        estoqueRepository.save(estoque);

    }

    public void definirQuantidade(Livro livro, int novaQuantidade) {
        if (novaQuantidade < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade não pode ser negativa.");
        }

        Estoque estoque = estoqueRepository.findById(livro.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado para o livro"));
        
        estoque.setQuantidadeDisponivel(novaQuantidade);
        estoqueRepository.save(estoque);
    }

}
