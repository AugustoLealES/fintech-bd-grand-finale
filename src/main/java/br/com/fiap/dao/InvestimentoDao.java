package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Investimento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDao {

    private Connection conn;

    public InvestimentoDao() throws SQLException {
        try {
            this.conn = ConnectionFactory.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método para inserir um investimento
    public void insert(Investimento investimento) {
        String sql = "INSERT INTO Investimento (id_cliente, valor_investido, data_investimento, tipo_investimento, retorno_estimado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, investimento.getIdCliente());
            stmt.setDouble(2, investimento.getValorInvestido());
            stmt.setDate(3, Date.valueOf(investimento.getDataInvestimento()));
            stmt.setString(4, investimento.getTipoInvestimento());
            stmt.setDouble(5, investimento.getRetornoEstimado());
            stmt.executeUpdate();
            System.out.println("Investimento inserido com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir investimento: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Método para buscar investimentos por ID do cliente
    public List<Investimento> getByClienteId(int idCliente) {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT * FROM Investimento WHERE id_cliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Investimento investimento = new Investimento();
                    investimento.setId(rs.getInt("ID_INVESTIMENTO"));
                    investimento.setValorInvestido(rs.getDouble("VALOR_INVESTIDO"));
                    investimento.setDataInvestimento(rs.getDate("DATA_INVESTIMENTO").toLocalDate());
                    investimento.setTipoInvestimento(rs.getString("TIPO_INVESTIMENTO"));
                    investimento.setRetornoEstimado(rs.getDouble("RETORNO_ESTIMADO"));
                    investimentos.add(investimento);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar investimentos para o cliente com ID: " + idCliente);
            e.printStackTrace();
        }

        return investimentos;
    }

    // Método para atualizar o investimento (se necessário)
    public void update(Investimento investimento) {
        String sql = "UPDATE Investimento SET valor_investido = ?, data_investimento = ?, tipo_investimento = ?, retorno_estimado = ? WHERE id_investimento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, investimento.getValorInvestido());
            stmt.setDate(2, Date.valueOf(investimento.getDataInvestimento()));
            stmt.setString(3, investimento.getTipoInvestimento());
            stmt.setDouble(4, investimento.getRetornoEstimado());
            stmt.setInt(5, investimento.getId());
            stmt.executeUpdate();
            System.out.println("Investimento atualizado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar investimento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para deletar um investimento
    public void delete(int idInvestimento) {
        String sql = "DELETE FROM Investimento WHERE id_investimento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInvestimento);
            stmt.executeUpdate();
            System.out.println("Investimento deletado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar investimento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}