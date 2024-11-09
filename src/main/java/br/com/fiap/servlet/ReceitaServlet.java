package br.com.fiap.servlet;

import br.com.fiap.dao.ReceitaDao;
import br.com.fiap.model.Receita;
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

@WebServlet("/banco-receita")
public class ReceitaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ReceitaServlet.class.getName());
    private ReceitaDao receitaDao = new ReceitaDao();

    public ReceitaServlet() throws SQLException {
    }

    // Método doPost para inserir uma nova receita
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int idCliente = (int) session.getAttribute("userId");
        logger.info("ID do cliente: " + idCliente);
        double valor = Double.parseDouble(request.getParameter("valor"));
        String descricao = request.getParameter("descricao");
        String dataRecebimentoStr = request.getParameter("data_recebimento");

        try {
            LocalDate dataRecebimento = LocalDate.parse(dataRecebimentoStr);
            Receita receita = new Receita(0, idCliente, valor, dataRecebimento, descricao);
            receitaDao.insert(receita); // Chama o método insert do DAO
            response.sendRedirect("banco-receita");
        } catch (Exception e) {
            logger.severe("Erro ao adicionar receita: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro ao adicionar receita: " + e.getMessage());
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    // Método doGet para listar as receitas do cliente logado
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("userId");

        logger.info("ID do cliente: " + idCliente);

        // Verifica se o cliente está logado e a sessão está válida
        if (idCliente != null && idCliente != 0) {
            try {
                // Carrega as receitas automaticamente ao acessar a página ou quando o botão for pressionado
                List<Receita> receitas = receitaDao.getByClienteId(idCliente); // Chama o método para buscar as receitas
                request.setAttribute("receitas", receitas); // Define as receitas como atributo da requisição
                request.getRequestDispatcher("receitas.jsp").forward(request, response); // Encaminha para a página JSP
            } catch (SQLException e) {
                // Caso ocorra erro ao buscar as receitas, exibe a mensagem de erro
                logger.severe("Erro ao carregar as receitas: " + e.getMessage());
                request.setAttribute("errorMessage", "Erro ao carregar as receitas: " + e.getMessage());
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