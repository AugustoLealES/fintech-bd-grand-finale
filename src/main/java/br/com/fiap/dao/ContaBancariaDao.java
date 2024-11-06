package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaBancaria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaBancariaDao {

    // Método para inserir uma nova conta bancária
    public void insert(ContaBancaria conta) {
        String sql = "INSERT INTO ContaBancaria (id_conta, id_cliente, tipo_conta, saldo, data_abertura, agencia) " +
                "VALUES (seq_conta.nextval, ?, ?, ?, ?, ?)";

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

    // Método para obter todas as contas bancárias de um cliente específico
    public List<ContaBancaria> getAllByClienteId(int idCliente) {
        List<ContaBancaria> contas = new ArrayList<>();
        String sql = "SELECT * FROM ContaBancaria WHERE id_cliente = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ContaBancaria conta = new ContaBancaria();
                    conta.setIdConta(rs.getInt("id_conta"));
                    conta.setIdCliente(rs.getInt("id_cliente"));
                    conta.setTipoConta(rs.getString("tipo_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));
                    conta.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
                    conta.setAgencia(rs.getString("agencia"));
                    contas.add(conta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter contas bancárias: " + e.getMessage());
        }

        return contas;
    }

    // Método para atualizar o saldo de uma conta bancária
    public void updateSaldo(int idConta, double novoSaldo) {
        String sql = "UPDATE ContaBancaria SET saldo = ? WHERE id_conta = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, idConta);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo da conta bancária: " + e.getMessage());
        }
    }

    // Método para deletar uma conta bancária por ID
    public void delete(int idConta) {
        String sql = "DELETE FROM ContaBancaria WHERE id_conta = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar conta bancária: " + e.getMessage());
        }
    }
}