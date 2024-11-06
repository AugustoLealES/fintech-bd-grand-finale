package br.com.fiap.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Random;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.ContaBancaria;

@WebServlet("/login-conta")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Valida o usuário consultando o banco de dados
        if (validateUser(email, senha)) {
            // Se o login for bem-sucedido, cria uma nova sessão
            HttpSession session = request.getSession();
            session.setAttribute("user", email);

            // Obtém o ID do cliente
            int idCliente = getIdClienteByEmail(email);
            // Obtém o nome do cliente
            String nomeUsuario = getNomeClienteByEmail(email);
            // Verifica se o cliente já possui uma conta bancária
            ContaBancaria contaBancaria = getAccountByClientId(idCliente);

            if (contaBancaria == null) {
                // Se a conta não existir, cria uma nova conta bancária
                contaBancaria = createRandomAccount(idCliente);
                insertAccount(contaBancaria);
            }

            // Armazena a conta bancária e o nome na sessão
            session.setAttribute("contaBancaria", contaBancaria);
            session.setAttribute("userName", nomeUsuario); // Define o nome do usuário
            session.setAttribute("userBalance", contaBancaria.getSaldo()); // Saldo da conta

            response.sendRedirect("home.jsp"); // Redireciona para a página inicial
        } else {
            // Se o login falhar, redireciona de volta com uma mensagem de erro
            request.setAttribute("errorMessage", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private boolean validateUser(String email, String senha) {
        boolean isValid = false;

        // Conexão com o banco de dados
        try (Connection conexao = ConnectionFactory.getConnection();) {
            String sql = "SELECT * FROM Cliente WHERE email = ? AND senha = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, senha); // Lembre-se de usar hashing para senhas em produção

                try (ResultSet rs = stmt.executeQuery()) {
                    isValid = rs.next(); // Se houver um resultado, o usuário é válido
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente em um projeto real
        }

        return isValid;
    }

    private int getIdClienteByEmail(String email) {
        int idCliente = -1;

        // Conexão com o banco de dados
        try (Connection conexao = ConnectionFactory.getConnection();) {
            String sql = "SELECT id_cliente FROM Cliente WHERE email = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idCliente = rs.getInt("id_cliente");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente em um projeto real
        }

        return idCliente;
    }

    private String getNomeClienteByEmail(String email) {
        String nome = null;

        // Conexão com o banco de dados
        try (Connection conexao = ConnectionFactory.getConnection();) {
            String sql = "SELECT nome FROM Cliente WHERE email = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        nome = rs.getString("nome");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente em um projeto real
        }

        return nome;
    }

    private ContaBancaria getAccountByClientId(int idCliente) {
        ContaBancaria contaBancaria = null;

        // Conexão com o banco de dados
        try (Connection conexao = ConnectionFactory.getConnection();) {
            String sql = "SELECT * FROM ContaBancaria WHERE id_cliente = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        contaBancaria = new ContaBancaria();
                        contaBancaria.setIdCliente(rs.getInt("id_cliente"));
                        contaBancaria.setTipoConta(rs.getString("tipo_conta"));
                        contaBancaria.setSaldo(rs.getDouble("saldo"));
                        contaBancaria.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
                        contaBancaria.setAgencia(rs.getString("agencia"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente em um projeto real
        }

        return contaBancaria;
    }

    private ContaBancaria createRandomAccount(int idCliente) {
        Random random = new Random();
        ContaBancaria contaBancaria = new ContaBancaria();

        contaBancaria.setIdCliente(idCliente);
        contaBancaria.setTipoConta(random.nextBoolean() ? "corrente" : "poupanca");
        contaBancaria.setSaldo(random.nextDouble() * 1000); // Saldo aleatório entre 0 e 1000
        contaBancaria.setDataAbertura(LocalDate.from(java.time.LocalDateTime.now()));
        // Gera um número aleatório de agência de 4 dígitos
        int agenciaNumero = 1000 + random.nextInt(9000); // Garante que seja entre 1000 e 9999
        contaBancaria.setAgencia(String.valueOf(agenciaNumero)); // Armazena como String

        return contaBancaria;
    }

    private void insertAccount(ContaBancaria contaBancaria) {
        // Insere a nova conta bancária no banco de dados
        try (Connection conexao = ConnectionFactory.getConnection();) {
            String sql = "INSERT INTO ContaBancaria (id_cliente, tipo_conta, saldo, data_abertura, agencia) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, contaBancaria.getIdCliente());
                stmt.setString(2, contaBancaria.getTipoConta());
                stmt.setDouble(3, contaBancaria.getSaldo());
                stmt.setDate(4, java.sql.Date.valueOf(contaBancaria.getDataAbertura()));
                stmt.setString(5, contaBancaria.getAgencia());
                stmt.executeUpdate(); // Executa o insert
            }
        } catch (Exception e) {
            e.printStackTrace(); // Tratar exceções adequadamente em um projeto real
        }
    }
}