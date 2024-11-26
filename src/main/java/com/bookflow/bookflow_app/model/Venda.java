package com.bookflow.bookflow_app.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeCliente;
    private String cpfCliente;
    private LocalDate dataVenda;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name= "livro_id")
    private Livro livro;

    private int quantidade; 

    private double total;

    public void calcularTotal() {
        if(livro != null){
            this.total = livro.getPreco() * quantidade;
        } else {
            this.total = 0.0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    
}

