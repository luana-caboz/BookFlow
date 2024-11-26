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
            Venda novaVenda = vendaService.criarVenda(venda);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Venda criada com sucesso! ID: " + novaVenda.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable int id) {
        try {
            Venda venda = vendaService.buscarVendaPorId(id);
            return ResponseEntity.ok(venda);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarTodasAsVendas();
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarVenda(@PathVariable int id, @RequestBody Venda vendaAtualizada) {
        try {
            Venda vendaAtualizadaResp = vendaService.atualizarVenda(id, vendaAtualizada);
            return ResponseEntity.ok("Venda atualizada com sucesso! ID: " + vendaAtualizadaResp.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarVenda(@PathVariable int id) {
        try {
            vendaService.cancelarVenda(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
