package br.com.fiap.service;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaBancaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class UserService {

    public boolean validateUser(String email, String senha) {
        String storedPassword = getPasswordByEmail(email);
        return storedPassword != null && senha.equals(storedPassword);
    }

    public int getClientIdByEmail(String email) {
        String query = "SELECT id_cliente FROM Cliente WHERE email = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Melhore o tratamento em produção
        }
        return -1;
    }

    public String getClientNameByEmail(String email) {
        String query = "SELECT nome FROM Cliente WHERE email = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Melhore o tratamento em produção
        }
        return null;
    }

    public ContaBancaria getOrCreateAccount(int clientId) {
        ContaBancaria conta = getAccountByClientId(clientId);
        if (conta == null) {
            conta = createRandomAccount(clientId);
            insertAccount(conta);
        }
        return conta;
    }

    private String getPasswordByEmail(String email) {
        String query = "SELECT senha FROM Cliente WHERE email = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("senha");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Melhore o tratamento em produção
        }
        return null;
    }

    private ContaBancaria getAccountByClientId(int clientId) {
        String query = "SELECT * FROM ContaBancaria WHERE id_cliente = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ContaBancaria conta = new ContaBancaria();
                conta.setIdCliente(clientId);
                conta.setTipoConta(rs.getString("tipo_conta"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
                conta.setAgencia(rs.getString("agencia"));
                return conta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ContaBancaria createRandomAccount(int clientId) {
        Random random = new Random();
        ContaBancaria conta = new ContaBancaria();
        conta.setIdCliente(clientId);
        conta.setTipoConta(random.nextBoolean() ? "corrente" : "poupanca");
        conta.setSaldo(random.nextDouble() * 1000);
        conta.setDataAbertura(LocalDate.now());
        conta.setAgencia(String.valueOf(1000 + random.nextInt(9000)));
        return conta;
    }

    private void insertAccount(ContaBancaria conta) {
        String query = "INSERT INTO ContaBancaria (id_cliente, tipo_conta, saldo, data_abertura, agencia) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, conta.getIdCliente());
            stmt.setString(2, conta.getTipoConta());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setDate(4, java.sql.Date.valueOf(conta.getDataAbertura()));
            stmt.setString(5, conta.getAgencia());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Melhore o tratamento em produção
        }
    }
}