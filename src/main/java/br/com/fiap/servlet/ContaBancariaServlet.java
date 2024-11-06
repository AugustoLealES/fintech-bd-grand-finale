package br.com.fiap.servlet;

import br.com.fiap.dao.ContaBancariaDao;
import br.com.fiap.model.ContaBancaria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet({"/banco-conta"})
public class ContaBancariaServlet extends HttpServlet {
    private ContaBancariaDao contaDao = new ContaBancariaDao();

    public ContaBancariaServlet() {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
        String tipoConta = request.getParameter("tipo_conta");
        double saldo = Double.parseDouble(request.getParameter("saldo"));
        String dataAberturaStr = request.getParameter("data_abertura");
        String agencia = request.getParameter("agencia");

        // Parse a data para LocalDate usando DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataAbertura = LocalDate.parse(dataAberturaStr, formatter);

        // Cria o objeto ContaBancaria com LocalDate
        ContaBancaria conta = new ContaBancaria(idCliente, tipoConta, saldo, dataAbertura, agencia);

        contaDao.insert(conta);
        response.sendRedirect("sucesso.jsp");
    }


}