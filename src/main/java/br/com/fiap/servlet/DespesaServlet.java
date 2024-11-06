//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.fiap.servlet;

import br.com.fiap.dao.DespesaDao;
import br.com.fiap.model.Despesa;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet({"/banco-despesa"})
public class DespesaServlet extends HttpServlet {
    private DespesaDao despesaDao = new DespesaDao();

    public DespesaServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
        double valor = Double.parseDouble(request.getParameter("valor"));
        String dataPagamentoStr = request.getParameter("data_pagamento");
        String descricao = request.getParameter("descricao");
        LocalDate dataPagamento = LocalDate.parse(dataPagamentoStr);
        Despesa despesa = new Despesa(id, idCliente, valor, dataPagamento, descricao);
        this.despesaDao.insert(despesa);
        response.sendRedirect("sucesso.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Despesa> despesas = this.despesaDao.getAll();
        request.setAttribute("despesas", despesas);
        request.getRequestDispatcher("despesas.jsp").forward(request, response);
    }
}
