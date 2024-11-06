package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Investimento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDao {

    public void insert(Investimento investimento) {
        String sql = "INSERT INTO Investimento (id_cliente, valor_investido, data_investimento, tipo_investimento, retorno_estimado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, investimento.getIdCliente());
            stmt.setDouble(2, investimento.getValorInvestido());
            stmt.setDate(3, Date.valueOf(investimento.getDataInvestimento()));
            stmt.setString(4, investimento.getTipoInvestimento());
            stmt.setDouble(5, investimento.getRetornoEstimado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Investimento> getAll() {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT * FROM Investimento";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Investimento investimento = new Investimento();
                investimento.setId(rs.getInt("id_investimento"));
                investimento.setIdCliente(rs.getInt("id_cliente"));
                investimento.setValorInvestido(rs.getDouble("valor_investido"));
                investimento.setDataInvestimento(rs.getDate("data_investimento").toLocalDate());
                investimento.setTipoInvestimento(rs.getString("tipo_investimento"));
                investimento.setRetornoEstimado(rs.getDouble("retorno_estimado"));
                investimentos.add(investimento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return investimentos;
    }
}