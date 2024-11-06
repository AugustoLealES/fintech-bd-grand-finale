package br.com.fiap.model;

import java.time.LocalDate;

public class Despesa {
    private int id;
    private int idCliente;
    private double valor;
    private LocalDate dataPagamento;
    private String descricao;

    public Despesa() {}

    // Construtor com parâmetros
    public Despesa(int id, int idCliente, double valor, LocalDate dataPagamento, String descricao) {
        this.id = id; // Inicializa o id
        this.idCliente = idCliente;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}