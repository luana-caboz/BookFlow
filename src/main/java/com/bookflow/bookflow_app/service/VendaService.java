package com.bookflow.bookflow_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.Exceptions.EstoqueInsuficienteException;
import com.bookflow.bookflow_app.Exceptions.LivroNaoEncontradoException;
import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.repository.LivroRepository;
import com.bookflow.bookflow_app.repository.VendaRepository;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public Venda criarVenda(Venda venda) {
        // Cria uma nova venda
        venda.setDataVenda(LocalDate.now());
        venda.setTotal(0.0);

        validarVenda(venda);

        Livro livro = livroRepository.findById(venda.getLivro().getId())
                .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado"));

        if (livro.getQuantidadeDisponivel() < venda.getQuantidade()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o livro: " + livro.getTitulo());
        }

        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - venda.getQuantidade());
        livroRepository.save(livro);

        venda.setTotal(livro.getPreco() * venda.getQuantidade());
        venda.setDataVenda(LocalDate.now());
        venda.setStatus("Concluída");

        return vendaRepository.save(venda);
    }

    private void validarVenda(Venda venda) {
        if (venda.getNomeCliente() == null || venda.getNomeCliente().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do cliente é obrigatório.");
        }
        if (venda.getCpfCliente() == null || venda.getCpfCliente().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF do cliente é obrigatório.");
        }
        if (venda.getLivro() == null || venda.getQuantidade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Informações do livro e quantidade são obrigatórias.");
        }
    }

    public List<Venda> listarTodasAsVendas() {
        return vendaRepository.findAll();
    }

    public Venda buscarVendaPorId(int id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada."));

    }

    public Venda atualizarVenda(int vendaId, Venda novaVenda) {
        Venda vendaExistente = buscarVendaPorId(vendaId);

        Livro livroAntigo = vendaExistente.getLivro();
        livroAntigo.setQuantidadeDisponivel(livroAntigo.getQuantidadeDisponivel() + vendaExistente.getQuantidade());
        livroRepository.save(livroAntigo);

        // Validar e atualizar o novo livro e quantidade
        Livro livroNovo = livroRepository.findById(novaVenda.getLivro().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (livroNovo.getQuantidadeDisponivel() < novaVenda.getQuantidade()) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para o livro: " + livroNovo.getTitulo());
        }

        livroNovo.setQuantidadeDisponivel(livroNovo.getQuantidadeDisponivel() - novaVenda.getQuantidade());
        livroRepository.save(livroNovo);

        // Atualizar os dados da venda
        vendaExistente.setLivro(livroNovo);
        vendaExistente.setQuantidade(novaVenda.getQuantidade());
        vendaExistente.setNomeCliente(novaVenda.getNomeCliente());
        vendaExistente.setCpfCliente(novaVenda.getCpfCliente());
        vendaExistente.calcularTotal();

        return vendaRepository.save(vendaExistente);
    }

    @Transactional
    public void cancelarVenda(int vendaId) {
        Venda venda = buscarVendaPorId(vendaId);

        Livro livro = venda.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + venda.getQuantidade());
        livroRepository.save(livro);

        vendaRepository.deleteById(vendaId);
    }

}
