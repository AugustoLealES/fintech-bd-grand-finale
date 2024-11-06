package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Emprestimo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDao {

    public void insert(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimo (id_cliente, valor_emprestimo, data_contratacao, data_vencimento, juros, valor_pago, status_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getIdCliente());
            stmt.setDouble(2, emprestimo.getValorEmprestimo());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataContratacao()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataVencimento()));
            stmt.setDouble(5, emprestimo.getJuros());
            stmt.setDouble(6, emprestimo.getValorPago());
            stmt.setString(7, emprestimo.getStatusPagamento());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> getAll() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESTIMO";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id_emprestimo"));
                emprestimo.setIdCliente(rs.getInt("id_cliente"));
                emprestimo.setValorEmprestimo(rs.getDouble("valor_emprestimo"));
                emprestimo.setDataContratacao(rs.getDate("data_contratacao").toLocalDate());
                emprestimo.setDataVencimento(rs.getDate("data_vencimento").toLocalDate());
                emprestimo.setJuros(rs.getDouble("juros"));
                emprestimo.setValorPago(rs.getDouble("valor_pago"));
                emprestimo.setStatusPagamento(rs.getString("status_pagamento"));
                emprestimos.add(emprestimo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emprestimos;
    }
}