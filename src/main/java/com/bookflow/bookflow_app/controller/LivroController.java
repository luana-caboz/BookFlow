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
import com.bookflow.bookflow_app.service.EstoqueService;
import com.bookflow.bookflow_app.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final EstoqueService estoqueService;

    // Injeta os serviços no controlador
    public LivroController(LivroService livroService, EstoqueService estoqueService){
        this.livroService = livroService;
        this.estoqueService = estoqueService;
    }

    // Endpoint para adicionar um livro
    @PostMapping
    public ResponseEntity<String> adicionarLivro(@RequestBody Livro livro){
        try{
            livroService.adicionarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro adicionado com sucesso!");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    // Endpoint para adicionar quantidade ao estoque de um livro
    @PutMapping("/{id}/estoque/adicionar")
    public ResponseEntity<String> adicionarQuantidade(@PathVariable int id, @RequestBody int quantidadeAdicional) {
        try {
            Livro livro = livroService.buscarPorId(id);
            estoqueService.ajustarEstoque(livro, quantidadeAdicional);  // Usando o EstoqueService para ajustar o estoque
            return ResponseEntity.ok("Quantidade adicionada ao estoque com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros(){
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    // Endpoint para buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Livro livro = livroService.buscarPorId(id); 
            return ResponseEntity.ok(livro); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para atualizar informações de um livro
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable int id, @RequestBody Livro livroAtualizado) {
        try {
            livroService.atualizarLivro(id, livroAtualizado); 
            return ResponseEntity.ok("Livro atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para atualizar quantidade do livro no estoque
    @PutMapping("/{id}/estoque")
    public ResponseEntity<String> atualizarQuantidade(@PathVariable int id, @RequestBody int novaQuantidade) {
        try {
            Livro livro = livroService.buscarPorId(id);
            estoqueService.definirQuantidade(livro, novaQuantidade); // Usando o EstoqueService para definir a quantidade
            return ResponseEntity.ok("Quantidade do livro atualizada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para deletar um livro
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
