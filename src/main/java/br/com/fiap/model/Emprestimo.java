package br.com.fiap.model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private int idCliente;
    private double valorEmprestimo;
    private LocalDate dataContratacao;
    private LocalDate dataVencimento;
    private double juros;
    private double valorPago;
    private String statusPagamento;

    // Construtor vazio
    public Emprestimo() {}

    // Construtor com parâmetros
    public Emprestimo(int idCliente, double valorEmprestimo, LocalDate dataContratacao, LocalDate dataVencimento, double juros, double valorPago, String statusPagamento) {
        this.idCliente = idCliente;
        this.valorEmprestimo = valorEmprestimo;
        this.dataContratacao = dataContratacao;
        this.dataVencimento = dataVencimento;
        this.juros = juros;
        this.valorPago = valorPago;
        this.statusPagamento = statusPagamento;
    }



    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public double getValorEmprestimo() { return valorEmprestimo; }
    public void setValorEmprestimo(double valorEmprestimo) { this.valorEmprestimo = valorEmprestimo; }

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public double getJuros() { return juros; }
    public void setJuros(double juros) { this.juros = juros; }

    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }

    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }
}