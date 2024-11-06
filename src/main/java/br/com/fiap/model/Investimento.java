package br.com.fiap.model;

import java.time.LocalDate;

public class Investimento {
    private int id;
    private int idCliente;
    private double valorInvestido;
    private LocalDate dataInvestimento;
    private String tipoInvestimento;
    private double retornoEstimado;

    // Construtor vazio
    public Investimento() {}

    // Construtor com parâmetros
    public Investimento(int idCliente, double valorInvestido, LocalDate dataInvestimento, String tipoInvestimento, double retornoEstimado) {
        this.idCliente = idCliente;
        this.valorInvestido = valorInvestido;
        this.dataInvestimento = dataInvestimento;
        this.tipoInvestimento = tipoInvestimento;
        this.retornoEstimado = retornoEstimado;
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

    public double getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(double valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public LocalDate getDataInvestimento() {
        return dataInvestimento;
    }

    public void setDataInvestimento(LocalDate dataInvestimento) {
        this.dataInvestimento = dataInvestimento;
    }

    public String getTipoInvestimento() {
        return tipoInvestimento;
    }

    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    public double getRetornoEstimado() {
        return retornoEstimado;
    }

    public void setRetornoEstimado(double retornoEstimado) {
        this.retornoEstimado = retornoEstimado;
    }
}