package com.bookflow.bookflow_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookflow.bookflow_app.model.Venda;
import com.bookflow.bookflow_app.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        Venda novaVenda = vendaService.criarVenda(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarTodasAsVendas() {
        List<Venda> vendas = vendaService.listarTodasAsVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        return vendaService.buscarVendaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
