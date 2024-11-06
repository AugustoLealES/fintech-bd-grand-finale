//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package br.com.fiap.servlet;

import br.com.fiap.dao.ReceitaDao;
import br.com.fiap.model.Receita;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet({"/banco-receita"})
public class ReceitaServlet extends HttpServlet {
    private ReceitaDao receitaDao = new ReceitaDao();

    public ReceitaServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("id_cliente"));
        double valor = Double.parseDouble(request.getParameter("valor"));
        String descricao = request.getParameter("descricao");
        String dataRecebimentoStr = request.getParameter("data_recebimento");
        LocalDate dataRecebimento = LocalDate.parse(dataRecebimentoStr);
        Receita receita = new Receita(0, idCliente, valor, dataRecebimento, descricao);
        this.receitaDao.insert(receita);
        response.sendRedirect("sucesso.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Receita> receitas = this.receitaDao.getAll();
        request.setAttribute("receitas", receitas);
        request.getRequestDispatcher("receitas.jsp").forward(request, response);
    }
}
