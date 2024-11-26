package com.bookflow.bookflow_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Livro;
import com.bookflow.bookflow_app.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<String> adicionarLivro(@RequestBody Livro livro){
        try{
            System.out.println("Livro" +livro.getTitulo()+ " recebido!");
            livroService.adicionarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro adicionado com sucesso!");
        } catch (ResponseStatusException e) {
            System.err.println("Erro ao adicionar livro: " + e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros(){
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Livro livro = livroService.buscarPorId(id); 
            return ResponseEntity.ok(livro); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable int id, @RequestBody Livro livroAtualizado) {
        try {
            livroService.atualizarLivro(id, livroAtualizado); 
            return ResponseEntity.ok("Livro atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<String> atualizarQuantidade(@PathVariable int id, @RequestBody int novaQuantidade) {
        try {
            Livro livro = livroService.buscarPorId(id);
            livroService.atualizarQuantidade(livro, novaQuantidade);  
            return ResponseEntity.ok("Quantidade do livro atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable int id) {
        try {
            livroService.deletarLivro(id);  
            return ResponseEntity.ok("Livro deletado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
