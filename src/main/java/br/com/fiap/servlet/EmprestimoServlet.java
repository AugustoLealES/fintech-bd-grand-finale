//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.fiap.servlet;

import br.com.fiap.dao.EmprestimoDao;
import br.com.fiap.model.Emprestimo;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet({"/banco-emprestimo"})
public class EmprestimoServlet extends HttpServlet {
    private EmprestimoDao emprestimoDao = new EmprestimoDao();

    public EmprestimoServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
        double valorEmprestimo = Double.parseDouble(request.getParameter("valor"));
        String dataContratacaoStr = request.getParameter("data_contratacao");
        String dataVencimentoStr = request.getParameter("data_vencimento");
        double juros = Double.parseDouble(request.getParameter("juros"));
        double valorPago = Double.parseDouble(request.getParameter("valor_pago"));
        String statusPagamento = request.getParameter("status_pagamento");
        LocalDate dataContratacao = LocalDate.parse(dataContratacaoStr);
        LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
        Emprestimo emprestimo = new Emprestimo(idCliente, valorEmprestimo, dataContratacao, dataVencimento, juros, valorPago, statusPagamento);
        this.emprestimoDao.insert(emprestimo);
        response.sendRedirect("sucesso.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Emprestimo> emprestimos = emprestimoDao.getAll();  // Chama o método que busca os empréstimos
        if (emprestimos != null && !emprestimos.isEmpty()) {
            request.setAttribute("emprestimos", emprestimos);  // Passa os dados para o JSP
            request.getRequestDispatcher("emprestimos.jsp").forward(request, response);  // Redireciona para o JSP
        } else {
            request.setAttribute("mensagem", "Nenhum empréstimo encontrado.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);  // Redireciona para uma página de erro
        }
    }




}
