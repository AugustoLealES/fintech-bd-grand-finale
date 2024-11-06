package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Despesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDao {

    public void insert(Despesa despesa) {
        String sql = "INSERT INTO Despesa (id_cliente, valor, data_pagamento, descricao) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, despesa.getIdCliente());
            stmt.setDouble(2, despesa.getValor());
            stmt.setDate(3, Date.valueOf(despesa.getDataPagamento()));
            stmt.setString(4, despesa.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Despesa> getAll() {
        List<Despesa> despesas = new ArrayList<>();
        String sql = "SELECT * FROM Despesa";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id_despesa"));
                despesa.setIdCliente(rs.getInt("id_cliente"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());
                despesa.setDescricao(rs.getString("descricao"));
                despesas.add(despesa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return despesas;
    }
}