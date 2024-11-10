package br.com.fiap.servlet;

import br.com.fiap.model.ContaBancaria;
import br.com.fiap.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login-conta")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Validar se o usuário existe
        if (userService.validateUser(email, senha)) {
            // Criação ou recuperação da sessão
            HttpSession session = request.getSession(true);  // true garante que a sessão seja criada caso não exista
            session.setMaxInactiveInterval(30 * 60); // 30 minutos de inatividade

            // Armazenar apenas o ID do cliente, nome e email, evitando dados sensíveis
            int idCliente = userService.getClientIdByEmail(email);
            String nomeUsuario = userService.getClientNameByEmail(email);
            ContaBancaria contaBancaria = userService.getOrCreateAccount(idCliente);

            // Armazenar no session o ID do usuário, nome, email, conta e saldo
            session.setAttribute("userId", idCliente);
            session.setAttribute("userName", nomeUsuario);
            session.setAttribute("userEmail", email);  // Armazenando o email na sessão
            session.setAttribute("contaBancaria", contaBancaria);
            session.setAttribute("userBalance", contaBancaria.getSaldo());

            // Redirecionar para a página inicial
            response.sendRedirect("home.jsp");
        } else {
            // Se não for válido, exibir uma mensagem de erro
            request.setAttribute("errorMessage", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}