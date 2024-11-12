package com.bookflow.bookflow_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    private Integer livroId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private int quantidadeDisponivel;

    public Estoque(){
        
    }

    public Estoque(Livro livro, int quantidadeDisponivel) {
        this.livro = livro;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public boolean verificarEstoque(int quantidadeRequerida) {
        return quantidadeDisponivel >= quantidadeRequerida;
    }

    public void atualizarEstoque(int quantidadeVendida) {
        quantidadeDisponivel -= quantidadeVendida;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    
}

