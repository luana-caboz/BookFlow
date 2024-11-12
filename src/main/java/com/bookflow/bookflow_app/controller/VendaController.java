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

import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<String> criarVenda(@RequestBody Venda venda) {
        try {
            Venda vendaCriada = vendaService.criarVenda(venda);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Venda criada com sucesso! ID: " + vendaCriada.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable int id) {
        try {
            Venda venda = vendaService.buscarVendaPorId(id)
                    .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + id));
            return ResponseEntity.ok(venda);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        return ResponseEntity.ok(vendaService.listarTodasAsVendas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarVenda(@PathVariable int id, @RequestBody Venda novaVenda) {
        try {
            Venda vendaAtualizada = vendaService.atualizarVenda(id, novaVenda);
            return ResponseEntity.ok("Venda atualizada com sucesso! ID: " + vendaAtualizada.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarVenda(@PathVariable int id) {
        try {
            vendaService.cancelarVenda(id);
            return ResponseEntity.ok("Venda cancelada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
