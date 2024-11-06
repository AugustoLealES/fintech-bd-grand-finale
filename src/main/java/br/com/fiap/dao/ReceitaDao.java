package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao {

    public void insert(Receita receita) {
        String sql = "INSERT INTO Receita (id_cliente, valor, data_recebimento, descricao) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, receita.getIdCliente());
            stmt.setDouble(2, receita.getValor());
            stmt.setDate(3, Date.valueOf(receita.getDataRecebimento()));
            stmt.setString(4, receita.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Receita> getAll() {
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT * FROM Receita";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id_receita"));
                receita.setIdCliente(rs.getInt("id_cliente"));
                receita.setValor(rs.getDouble("valor"));
                receita.setDataRecebimento(rs.getDate("data_recebimento").toLocalDate());
                receita.setDescricao(rs.getString("descricao"));
                receitas.add(receita);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return receitas;
    }
}