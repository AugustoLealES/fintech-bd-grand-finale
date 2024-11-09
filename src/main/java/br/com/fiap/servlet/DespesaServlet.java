package br.com.fiap.servlet;

import br.com.fiap.dao.DespesaDao;
import br.com.fiap.model.Despesa;
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

@WebServlet("/banco-despesa")
public class DespesaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DespesaServlet.class.getName());
    private DespesaDao despesaDao = new DespesaDao();

    public DespesaServlet() throws SQLException {
    }

    // Método doPost para inserir uma nova despesa
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int idCliente = (int) session.getAttribute("userId");
        double valor = Double.parseDouble(request.getParameter("valor"));
        String descricao = request.getParameter("descricao");
        String dataPagamentoStr = request.getParameter("data_pagamento");

        try {
            LocalDate dataPagamento = LocalDate.parse(dataPagamentoStr);
            Despesa despesa = new Despesa(0, idCliente, valor, dataPagamento, descricao);
            despesaDao.insert(despesa); // Chama o método insert do DAO
            response.sendRedirect("banco-despesa");
        } catch (Exception e) {
            logger.severe("Erro ao adicionar despesa: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro ao adicionar despesa: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    // Método doGet para listar as despesas do cliente logado
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("userId");

        logger.info("ID do cliente: " + idCliente);

        // Verifica se o cliente está logado e a sessão está válida
        if (idCliente != null && idCliente != 0) {
            try {
                // Busca as despesas do cliente logado
                List<Despesa> despesas = despesaDao.getByClienteId(idCliente); // Método para buscar as despesas do cliente
                request.setAttribute("despesas", despesas); // Define as despesas como atributo da requisição
                request.getRequestDispatcher("despesas.jsp").forward(request, response); // Encaminha para a página JSP
            } catch (SQLException e) {
                // Caso ocorra erro ao buscar as despesas, exibe a mensagem de erro
                logger.severe("Erro ao carregar as despesas: " + e.getMessage());
                request.setAttribute("errorMessage", "Erro ao carregar as despesas: " + e.getMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response); // Encaminha para página de erro
            }
        } else {
            // Se o cliente não estiver logado ou a sessão tiver expirado
            logger.warning("Cliente não encontrado ou sessão expirada.");
            request.setAttribute("errorMessage", "Cliente não encontrado ou sessão expirada.");
            request.getRequestDispatcher("erro.jsp").forward(request, response); // Redireciona para erro
        }
    }
}