package com.bookflow.bookflow_app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
    
    @Transactional
    public Livro adicionarLivro(Livro livro) {
        validarLivro(livro, false);
        return livroRepository.save(livro);
    }

    private void validarLivro(Livro livro, boolean isUpdate) {
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O titulo do livro é obrigatório.");
        }
        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do autor é obrigatório.");
        }
        if (livro.getCategoria() == null || livro.getCategoria().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria do livro é obrigatória.");
        }
        if (livro.getPreco() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O preço do livro é obrigatório e deve ser maior que zero.");
        }
        if (livro.getDataPublicacao() == null || livro.getDataPublicacao().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A data de publicação é obrigatória e deve ser igual ou anterior a data atual.");
        }
        if (livro.getQuantidadeDisponivel() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade disponível não pode ser negativa.");
        }

        if (!isUpdate) {
            Optional<Livro> livroExistente = livroRepository.findByTitulo(livro.getTitulo());
            if (livroExistente.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Já existe livro cadastrado com este título.");
            }
        }
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(int id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            return livro.get();
        } else {
            throw new RuntimeException("Livro com o id " + id + " não encontrado.");
        }
    }

    @Transactional
    public Livro atualizarLivro(int id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);

        validarLivro(livroAtualizado, true);

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setCategoria(livroAtualizado.getCategoria());
        livro.setPreco(livroAtualizado.getPreco());
        livro.setDataPublicacao(livroAtualizado.getDataPublicacao());
        livro.setQuantidadeDisponivel(livroAtualizado.getQuantidadeDisponivel());

        return livroRepository.save(livro);
    }

    @Transactional
    public void atualizarQuantidade(Livro livro, int novaQuantidade) {
        if (novaQuantidade < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade não pode ser negativa.");
        }
        livro.setQuantidadeDisponivel(novaQuantidade);
        livroRepository.save(livro);
    }

    @Transactional
    public void deletarLivro(int id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}