package br.com.fiap.servlet;

import br.com.fiap.dao.InvestimentoDao;
import br.com.fiap.model.Investimento;
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

@WebServlet("/banco-investimento")
public class InvestimentoServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(InvestimentoServlet.class.getName());
    private InvestimentoDao investimentoDao = new InvestimentoDao();

    public InvestimentoServlet() throws SQLException {
        // Construtor, inicialização de recursos
    }

    // Método doPost para inserir um novo investimento
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
            // Recuperação dos parâmetros da requisição
            String valorInvestidoStr = request.getParameter("VALOR_INVESTIDO");
            String dataInvestimentoStr = request.getParameter("DATA_INVESTIMENTO");
            String tipoInvestimento = request.getParameter("TIPO_INVESTIMENTO");
            String retornoEstimadoStr = request.getParameter("RETORNO_ESTIMADO");

            // Log dos parâmetros para depuração
            logger.info("Parâmetros recebidos: ");
            logger.info("Valor Investido: " + valorInvestidoStr);
            logger.info("Data Investimento: " + dataInvestimentoStr);
            logger.info("Tipo Investimento: " + tipoInvestimento);
            logger.info("Retorno Estimado: " + retornoEstimadoStr);

            // Verificação de campos obrigatórios
            if (valorInvestidoStr == null || valorInvestidoStr.trim().isEmpty() ||
                    dataInvestimentoStr == null || dataInvestimentoStr.trim().isEmpty() ||
                    tipoInvestimento == null || tipoInvestimento.trim().isEmpty() ||
                    retornoEstimadoStr == null || retornoEstimadoStr.trim().isEmpty()) {

                request.setAttribute("errorMessage", "Todos os campos são obrigatórios.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
                return;
            }

            // Conversão dos valores numéricos e das datas
            double valorInvestido = Double.parseDouble(valorInvestidoStr);
            LocalDate dataInvestimento = LocalDate.parse(dataInvestimentoStr);
            double retornoEstimado = Double.parseDouble(retornoEstimadoStr);

            // Criação do objeto Investimento
            Investimento investimento = new Investimento(idCliente, valorInvestido, dataInvestimento, tipoInvestimento, retornoEstimado);

            // Log para depuração antes de inserir no banco
            logger.info("Tentando inserir investimento: " + investimento);

            // Inserção no banco de dados
            investimentoDao.insert(investimento);

            // Redirecionamento para a página de sucesso
            response.sendRedirect("banco-investimento");

        } catch (NumberFormatException e) {
            // Erro de conversão de número
            logger.severe("Erro ao converter valor numérico: " + e.getMessage());
            request.setAttribute("errorMessage", "Valor numérico inválido.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (Exception e) {
            // Exceção genérica
            logger.severe("Erro inesperado ao adicionar investimento: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro inesperado ao adicionar investimento.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }

    // Método doGet para listar os investimentos
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idCliente = (Integer) session.getAttribute("userId");

        if (idCliente != null && idCliente != 0) {
            try {
                // Busca os investimentos para o cliente logado
                List<Investimento> investimentos = investimentoDao.getByClienteId(idCliente);
                request.setAttribute("investimentos", investimentos);  // Passa os dados para o JSP
                request.getRequestDispatcher("investimentos.jsp").forward(request, response);  // Redireciona para a página JSP
            } catch (Exception e) {
                logger.severe("Erro ao carregar os investimentos: " + e.getMessage());
                request.setAttribute("errorMessage", "Erro ao carregar os investimentos: " + e.getMessage());
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            logger.warning("Cliente não encontrado ou sessão expirada.");
            request.setAttribute("errorMessage", "Cliente não encontrado ou sessão expirada.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}