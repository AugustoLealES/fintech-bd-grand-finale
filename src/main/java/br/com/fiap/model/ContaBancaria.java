package br.com.fiap.model;

import java.time.LocalDate;

public class ContaBancaria {
    private int idConta;
    private int idCliente;
    private String tipoConta;
    private double saldo;
    private LocalDate dataAbertura;
    private String agencia;

    // Construtor vazio
    public ContaBancaria() {}

    // Construtor com parâmetros
    public ContaBancaria(int idCliente, String tipoConta, double saldo, LocalDate dataAbertura, String agencia) {
        this.idCliente = idCliente;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.dataAbertura = dataAbertura;
        this.agencia = agencia;
    }

    // Getters e Setters
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }
}