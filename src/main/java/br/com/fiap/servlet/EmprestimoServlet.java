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

    public EmprestimoServlet() throws SQLException {
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
            // Recuperação dos parâmetros da requisição
            String valorEmprestimoStr = request.getParameter("VALOR_CONTRATACAO");
            String dataContratacaoStr = request.getParameter("DATA_CONTRATACAO");
            String dataVencimentoStr = request.getParameter("DATA_VENCIMENTO");
            String jurosStr = request.getParameter("JUROS");
            String valorPagoStr = request.getParameter("VALOR_PAGO");
            String statusPagamento = request.getParameter("STATUS_PAGAMENTO");

            // Log dos parâmetros para depuração
            logger.info("Parâmetros recebidos: ");
            logger.info("Valor Emprestimo: " + valorEmprestimoStr);
            logger.info("Data Contratação: " + dataContratacaoStr);
            logger.info("Data Vencimento: " + dataVencimentoStr);
            logger.info("Juros: " + jurosStr);
            logger.info("Valor Pago: " + valorPagoStr);
            logger.info("Status Pagamento: " + statusPagamento);

            // Verificação de campos obrigatórios
            if (valorEmprestimoStr == null || valorEmprestimoStr.trim().isEmpty() ||
                    dataContratacaoStr == null || dataContratacaoStr.trim().isEmpty() ||
                    dataVencimentoStr == null || dataVencimentoStr.trim().isEmpty() ||
                    jurosStr == null || jurosStr.trim().isEmpty() ||
                    valorPagoStr == null || valorPagoStr.trim().isEmpty() ||
                    statusPagamento == null || statusPagamento.trim().isEmpty()) {

                request.setAttribute("errorMessage", "Todos os campos são obrigatórios.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
                return;
            }

            // Conversão dos valores numéricos e das datas
            double valorEmprestimo = Double.parseDouble(valorEmprestimoStr);
            LocalDate dataContratacao = LocalDate.parse(dataContratacaoStr);
            LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);
            double juros = Double.parseDouble(jurosStr);
            double valorPago = Double.parseDouble(valorPagoStr);

            // Criação do objeto Emprestimo
            Emprestimo emprestimo = new Emprestimo(idCliente, valorEmprestimo, dataContratacao, dataVencimento, juros, valorPago, statusPagamento);

            // Log para depuração antes de inserir no banco
            logger.info("Tentando inserir empréstimo: " + emprestimo);

            // Inserção no banco de dados
            emprestimoDao.insert(emprestimo);

            // Redirecionamento para a página de sucesso
            response.sendRedirect("banco-emprestimo");

        } catch (NumberFormatException e) {
            // Erro de conversão de número
            logger.severe("Erro ao converter valor numérico: " + e.getMessage());
            request.setAttribute("errorMessage", "Valor numérico inválido.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        } catch (Exception e) {
            // Exceção genérica
            logger.severe("Erro inesperado ao adicionar empréstimo: " + e.getMessage());
            request.setAttribute("errorMessage", "Erro inesperado ao adicionar empréstimo.");
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