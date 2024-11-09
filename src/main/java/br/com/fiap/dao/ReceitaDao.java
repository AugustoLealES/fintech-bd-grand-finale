package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Receita;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao {

    private Connection conn;

    public ReceitaDao() throws SQLException {
        try {
            this.conn = ConnectionFactory.getConnection();  // Supondo que você tenha um gerenciador de conexões
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();  // Imprime o stack trace completo no console
            throw e;
        }
    }

    // Método para inserir uma receita
    public void insert(Receita receita) {
        String sql = "INSERT INTO receita (id_cliente, valor, data_recebimento, descricao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receita.getIdCliente());
            stmt.setDouble(2, receita.getValor());
            stmt.setDate(3, Date.valueOf(receita.getDataRecebimento()));
            stmt.setString(4, receita.getDescricao());
            stmt.executeUpdate();
            System.out.println("Receita inserida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir receita: " + e.getMessage());
            e.printStackTrace();  // Imprime o stack trace completo no console
        }
    }

    // Método para buscar receitas por id_cliente
    public List<Receita> getByClienteId(int idCliente) throws SQLException {
        List<Receita> receitas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Estabelece conexão com o banco
            conn = ConnectionFactory.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida.");

            String sql = "SELECT * FROM RECEITA WHERE ID_CLIENTE = ?";
            System.out.println("Executando SQL: " + sql);  // Exibe o SQL antes de executar

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();

            // Verifica se há dados retornados
            if (!rs.next()) {
                System.out.println("Nenhuma receita encontrada para o ID_CLIENTE: " + idCliente);
            } else {
                do {
                    Receita receita = new Receita();
                    receita.setId(rs.getInt("ID_RECEITA"));
                    receita.setValor(rs.getDouble("VALOR"));
                    receita.setDataRecebimento(rs.getDate("DATA_RECEBIMENTO").toLocalDate());
                    receita.setDescricao(rs.getString("DESCRICAO"));
                    receitas.add(receita);
                    System.out.println("Receita carregada: " + receita.getId());
                } while (rs.next());
            }



            System.out.println("Quantidade de receitas carregadas: " + receitas.size());
        } catch (SQLException e) {
            System.err.println("Erro ao consultar receitas para o cliente com ID: " + idCliente);
            e.printStackTrace();  // Imprime o stack trace completo no console
            // Lança a SQLException para ser capturada pelo servlet
            throw new SQLException("Erro ao consultar receitas", e);
        } finally {
            // Fechar todos os recursos no final
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return receitas;
    }
}