package com.bookflow.bookflow_app.Exceptions;

public class EstoqueInsuficienteException extends RuntimeException{
    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}
