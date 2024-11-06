package br.com.fiap;

import br.com.fiap.model.ContaBancaria;

import java.time.LocalDate;
import java.util.Random;

public class ContaBancariaGenerator {

    private static final Random random = new Random();

    public static ContaBancaria generateRandomContaBancaria(int idCliente) {
        // Gerar um número de conta aleatório
        String tipoConta = random.nextBoolean() ? "Corrente" : "Poupança"; // Tipo de conta aleatório
        double saldo = random.nextDouble() * 1000; // Saldo entre 0 e 1000
        String agencia = String.format("%04d", random.nextInt(10000)); // Gerar uma agência com 4 dígitos
        LocalDate dataAbertura = LocalDate.now(); // Data de abertura como a data atual

        // Criar e retornar uma nova ContaBancaria
        return new ContaBancaria(0, tipoConta, saldo, dataAbertura, agencia);
    }
}