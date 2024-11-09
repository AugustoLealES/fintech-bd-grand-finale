package br.com.fiap.servlet;

import br.com.fiap.dao.EmprestimoDao;
import br.com.fiap.model.Emprestimo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/banco-emprestimo")
public class EmprestimoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EmprestimoServlet.class.getName());
    private EmprestimoDao emprestimoDao = new EmprestimoDao();

    public EmprestimoServlet() {
    }

    // Método doPost para inserir um novo empréstimo
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("userId");

        if (idCliente == null || idCliente == 0) {
            // Caso não tenha cliente logado ou sessão expirada
            logger.warning("Cliente não encontrado ou sessão expirada.");
            request.setAttribute("errorMessage", "Cliente não encontrado ou sessão expirada.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
            return;
        }

        try {
            // Recuperando dados da requisição
            double valorEmprestimo = Double.parseDouble(request.getParameter("valor"));
            String dataContratacaoStr = request.getParameter("data_contratacao");
            String dataVencimentoStr = request.getParameter("data_vencimento");
            double juros = Double.parseDouble(request.getParameter("juros"));
            double valorPago = Double.parseDouble(request.getParameter("valor_pago"));
            String statusPagamento = request.getParameter("status_pagamento");

            // Convertendo as datas
            LocalDate dataContratacao = LocalDate.parse(dataContratacaoStr);
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);

            // Criando objeto Emprestimo
            Emprestimo emprestimo = new Emprestimo(idCliente, valorEmprestimo, dataContratacao, dataVencimento, juros, valorPago, statusPagamento);

            // Inserindo no banco de dados
            emprestimoDao.insert(emprestimo);

            // Redirecionando para a página de sucesso
            response.sendRedirect("sucesso.jsp");
        } catch (Exception e) {
            logger.severe("Erro ao adicionar empréstimo: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro ao adicionar empréstimo: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    // Método doGet para listar os empréstimos
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("userId");

        if (idCliente != null && idCliente != 0) {
            try {
                // Busca os empréstimos para o cliente logado
                List<Emprestimo> emprestimos = emprestimoDao.getByClienteId(idCliente);
                request.setAttribute("emprestimos", emprestimos);  // Passa os dados para o JSP
                request.getRequestDispatcher("emprestimos.jsp").forward(request, response);  // Redireciona para a página JSP
            } catch (SQLException e) {
                logger.severe("Erro ao carregar os empréstimos: " + e.getMessage());
                request.setAttribute("errorMessage", "Erro ao carregar os empréstimos: " + e.getMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            logger.warning("Cliente não encontrado ou sessão expirada.");
            request.setAttribute("errorMessage", "Cliente não encontrado ou sessão expirada.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}