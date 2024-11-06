//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.fiap.servlet;

import br.com.fiap.dao.InvestimentoDao;
import br.com.fiap.model.Investimento;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet({"/banco-investimento"})
public class InvestimentoServlet extends HttpServlet {
    private InvestimentoDao investimentoDao = new InvestimentoDao();

    public InvestimentoServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
        double valorInvestido = Double.parseDouble(request.getParameter("valor"));
        String dataInvestimentoStr = request.getParameter("data_investimento");
        String tipoInvestimento = request.getParameter("tipo");
        double retornoEstimado = Double.parseDouble(request.getParameter("retorno_estimado"));
        LocalDate dataInvestimento = LocalDate.parse(dataInvestimentoStr);
        Investimento investimento = new Investimento(idCliente, valorInvestido, dataInvestimento, tipoInvestimento, retornoEstimado);
        this.investimentoDao.insert(investimento);
        response.sendRedirect("sucesso.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Investimento> investimentos = this.investimentoDao.getAll();
        request.setAttribute("investimentos", investimentos);
        request.getRequestDispatcher("investimentos.jsp").forward(request, response);
    }
}
