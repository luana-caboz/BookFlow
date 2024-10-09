package com.bookflow.bookflow_app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro adicionarLivro(Livro livro) {
        validarLivro(livro);
        return livroRepository.save(livro);

    }

    private void validarLivro(Livro livro) {
        // validar campos obrigatórios
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O titulo do livro é obrigatório.");
        }
        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do autor é obrigatório.");
        }
        if (livro.getCategoria() == null || livro.getCategoria().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria do livro é obrigatória.");
        }
        if (livro.getPreco() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço do livro é obrigatório.");
        }
        if (livro.getDataPublicacao() == null || livro.getDataPublicacao().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de publicação é obrigatória e deve ser igual ou anterior a data atual.");
        }

        Optional<Livro> livroExistente = livroRepository.findByTituloContaining(livro.getTitulo());
        if (livroExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe livro cadastrado com este título.");
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
            throw new RuntimeException("Livro com o id "+ id +" não encontrado.");
        }
    }

    public Livro atualizarLivro(int id, Livro livroAtualizado){
        Livro livro = buscarPorId(id);
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setCategoria(livroAtualizado.getCategoria());
        livro.setPreco(livroAtualizado.getPreco());
        livro.setDataPublicacao(livroAtualizado.getDataPublicacao());
        return livroRepository.save(livro);
    }

    public void deletarLivro(int id){
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}