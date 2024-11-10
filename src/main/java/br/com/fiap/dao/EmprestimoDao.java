package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Emprestimo;
import br.com.fiap.model.Receita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDao {

    private Connection conn;

    public EmprestimoDao() throws SQLException {
        try {
            this.conn = ConnectionFactory.getConnection();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Método para inserir um empréstimo
    public void insert(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_cliente, valor_emprestimo, data_contratacao, data_vencimento, juros, valor_pago, status_pagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getIdCliente());
            stmt.setDouble(2, emprestimo.getValorEmprestimo());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataContratacao()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataVencimento()));
            stmt.setDouble(5, emprestimo.getJuros());
            stmt.setDouble(6, emprestimo.getValorPago());
            stmt.setString(7, emprestimo.getStatusPagamento());

            stmt.executeUpdate();
            System.out.println("Empréstimo inserido com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir empréstimo: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Método para buscar empréstimos por id_cliente (caso necessário no futuro)
    public List<Emprestimo> getByClienteId(int idCliente) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM EMPRESTIMO WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {  // Verifica se há registros no ResultSet
                    System.out.println("Nenhum empréstimo encontrado para o ID_CLIENTE: " + idCliente);
                }

                // Itera sobre os registros de empréstimos
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
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar empréstimos para o cliente com ID: " + idCliente);
            e.printStackTrace();  // Imprime o stack trace completo no console
            throw new SQLException("Erro ao consultar empréstimos", e);
        }

        return emprestimos;
    }
}