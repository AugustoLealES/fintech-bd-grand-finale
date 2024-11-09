package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Despesa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDao {

    // Método para inserir uma despesa
    public void insert(Despesa despesa) {
        String sql = "INSERT INTO Despesa (id_cliente, valor, data_pagamento, descricao) VALUES (?, ?, ?, ?)";

        // Tentando estabelecer a conexão e inserir a despesa
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, despesa.getIdCliente());
            stmt.setDouble(2, despesa.getValor());
            stmt.setDate(3, Date.valueOf(despesa.getDataPagamento()));
            stmt.setString(4, despesa.getDescricao());

            stmt.executeUpdate();
            System.out.println("Despesa inserida com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir despesa: " + e.getMessage());
            e.printStackTrace();  // Imprime o stack trace completo no console
        }
    }

    // Método para buscar todas as despesas


    // Método para buscar despesas por id_cliente
    public List<Despesa> getByClienteId(int idCliente) throws SQLException {
        List<Despesa> despesas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Estabelece conexão com o banco
            conn = ConnectionFactory.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida.");

            String sql = "SELECT * FROM DESPESA WHERE ID_CLIENTE = ?";
            System.out.println("Executando SQL: " + sql);  // Exibe o SQL antes de executar

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            rs = stmt.executeQuery();

            // Verifica se há dados retornados
            if (!rs.next()) {
                System.out.println("Nenhuma despesa encontrada para o ID_CLIENTE: " + idCliente);
            } else {
                do {
                    Despesa despesa = new Despesa();
                    despesa.setId(rs.getInt("id_despesa"));
                    despesa.setValor(rs.getDouble("valor"));
                    despesa.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());
                    despesa.setDescricao(rs.getString("descricao"));
                    despesas.add(despesa);
                    System.out.println("Despesa carregada: " + despesa.getId());
                } while (rs.next());
            }

            System.out.println("Quantidade de despesas carregadas: " + despesas.size());
        } catch (SQLException e) {
            System.err.println("Erro ao consultar despesas para o cliente com ID: " + idCliente);
            e.printStackTrace();  // Imprime o stack trace completo no console
            // Lança a SQLException para ser capturada pelo servlet
            throw new SQLException("Erro ao consultar despesas", e);
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

        return despesas;
    }
}