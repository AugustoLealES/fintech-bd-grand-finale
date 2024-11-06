package br.com.fiap.servlet;

import br.com.fiap.dao.ClienteDao;
import br.com.fiap.model.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@WebServlet("/banco-cliente")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDao clienteDao;

    @Override
    public void init() throws ServletException {
        clienteDao = new ClienteDao(); // Inicializa o DAO de clientes
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém os dados do cliente a partir da requisição
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String dataNascimentoStr = request.getParameter("dataNascimento");

        // Validação da data de nascimento
        if (dataNascimentoStr == null || dataNascimentoStr.isEmpty()) {
            request.setAttribute("mensagem", "A data de nascimento não pode ser vazia.");
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Redireciona para a tela de login
            return; // Retorna para não prosseguir
        }

        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(dataNascimentoStr);
        } catch (DateTimeParseException e) {
            request.setAttribute("mensagem", "Data de nascimento inválida. Formato esperado: AAAA-MM-DD.");
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Redireciona para a tela de login
            return; // Retorna para não prosseguir
        }

        // Cria um novo objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);
        cliente.setDataNascimento(dataNascimento);
        cliente.setCpf(cpf);

        // Insere o cliente no banco de dados
        try {
            Optional<Integer> resultado = clienteDao.insert(cliente);
            if (resultado.isPresent()) {
                request.setAttribute("mensagem", "Cliente cadastrado com sucesso!");
            } else {
                request.setAttribute("mensagem", "Cliente já existe com o CPF ou Email fornecido.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime a stack trace no console
            request.setAttribute("mensagem", "Ocorreu um erro inesperado: " + e.getMessage());
        }

        // Redireciona para a tela de login após o registro
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}