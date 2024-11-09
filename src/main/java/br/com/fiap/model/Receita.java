package br.com.fiap.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Receita {
    private int id;
    private int idCliente;
    private double valor;
    private LocalDate dataRecebimento;
    private String descricao;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public Receita() {}

    // Construtor com parâmetros
    public Receita(int id, int idCliente, double valor, LocalDate dataRecebimento, String descricao) {
        this.id = id; // Inicializa o id
        this.idCliente = idCliente;
        this.valor = valor;
        this.dataRecebimento = dataRecebimento;
        this.descricao = descricao;
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

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}