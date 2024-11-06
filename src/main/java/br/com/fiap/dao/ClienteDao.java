package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;
import br.com.fiap.model.ContaBancaria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ClienteDao {

    private static final Random random = new Random();

    public Optional<Integer> insert(Cliente cliente) {
        // Verificar se o cliente já existe
        if (exists(cliente.getCpf(), cliente.getEmail())) {
            System.out.println("Cliente já existe com o CPF ou Email fornecido.");
            return Optional.empty(); // Retornar vazio indicando que o cliente já existe
        }

        String sql = "INSERT INTO Cliente (nome, email, senha, data_nascimento, cpf) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Inserindo o cliente
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setDate(4, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(5, cliente.getCpf());
            stmt.executeUpdate();

            // Obtendo o ID do cliente inserido
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idCliente = generatedKeys.getInt(1); // ID gerado do cliente

                    // Gerar conta bancária aleatória
                    ContaBancaria conta = generateRandomContaBancaria(idCliente);

                    // Inserindo a conta bancária associada ao cliente
                    insertContaBancaria(conta);

                    return Optional.of(idCliente); // Retornar ID do cliente inserido

                } else {
                    throw new SQLException("Falha ao obter o ID gerado do cliente.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return Optional.empty(); // Retornar vazio em caso de erro
        }
    }

    private boolean exists(String cpf, String email) {
        String sql = "SELECT COUNT(*) FROM Cliente WHERE cpf = ? OR email = ?";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retornar true se já existe um cliente
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência do cliente: " + e.getMessage());
        }
        return false; // Retornar false se não existe cliente
    }

    private void insertContaBancaria(ContaBancaria conta) {
        String sql = "INSERT INTO ContaBancaria (id_cliente, tipo_conta, saldo, data_abertura, agencia) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, conta.getIdCliente());
            stmt.setString(2, conta.getTipoConta());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setDate(4, Date.valueOf(conta.getDataAbertura()));
            stmt.setString(5, conta.getAgencia());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir conta bancária: " + e.getMessage());
        }
    }

    private ContaBancaria generateRandomContaBancaria(int idCliente) {
        String tipoConta = random.nextBoolean() ? "Corrente" : "Poupança"; // Tipo de conta aleatório
        double saldo = random.nextDouble() * 1000; // Saldo entre 0 e 1000
        String agencia = String.format("%04d", random.nextInt(10000)); // Gerar uma agência com 4 dígitos
        LocalDate dataAbertura = LocalDate.now(); // Data de abertura como a data atual

        return new ContaBancaria(idCliente, tipoConta, saldo, dataAbertura, agencia);
    }

    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setCpf(rs.getString("cpf"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter clientes: " + e.getMessage());
        }

        return clientes;
    }
}