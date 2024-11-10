<%@ page import="br.com.fiap.model.Emprestimo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empréstimos</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Custom styles for the page */
        .modal-content {
            border-radius: 10px;
            padding: 20px;
        }
        .button {
            background-color: #FE555D;
        }
    </style>
</head>
<body class="bg-light">

<div class="container my-5">
    <h2 class="text-center mb-4">Lista de Empréstimos</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="alert alert-danger text-center">
        <%= errorMessage %>
    </div>
    <% } %>

    <%
        List<Emprestimo> emprestimos = (List<br.com.fiap.model.Emprestimo>) request.getAttribute("emprestimos");
        if (emprestimos != null && !emprestimos.isEmpty()) {
            // Ordenando a lista de empréstimos pelo ID
            Collections.sort(emprestimos, new Comparator<Emprestimo>() {
                public int compare(Emprestimo e1, Emprestimo e2) {
                    return Long.compare(e1.getId(), e2.getId());
                }
            });
    %>
    <table class="table table-bordered table-striped">
        <thead class="thead-light">
        <tr>
            <th>ID</th>
            <th>Valor</th>
            <th>Data de Contratação</th>
            <th>Data de Vencimento</th>
            <th>Juros</th>
            <th>Valor Pago</th>
            <th>Status Pagamento</th>
        </tr>
        </thead>
        <tbody>
        <% for (Emprestimo emprestimo : emprestimos) { %>
        <tr>
            <td><%= emprestimo.getId() %></td>
            <td>R$ <%= emprestimo.getValorEmprestimo() %></td>
            <td><%= emprestimo.getDataContratacao() %></td>
            <td><%= emprestimo.getDataVencimento() %></td>
            <td><%= emprestimo.getJuros() %> %</td>
            <td>R$ <%= emprestimo.getValorPago() %></td>
            <td><%= emprestimo.getStatusPagamento() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="text-center">Nenhum empréstimo encontrado para o usuário.</p>
    <% } %>

    <!-- Button to trigger modal -->
    <div class="text-center">
        <button class="btn btn-primary" id="openModalBtn">Adicionar Novo Empréstimo</button>
    </div>

    <!-- Modal -->
    <div id="myModal" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Adicionar Novo Empréstimo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="banco-emprestimo" method="post">
                        <input type="hidden" name="idCliente" value="<%= session.getAttribute("userId") %>" />
                        <div class="mb-3">
                            <label for="valorEmprestimo" class="form-label">Valor do Empréstimo:</label>
                            <input type="number" class="form-control" id="valorEmprestimo" name="VALOR_CONTRATACAO" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label for="dataContratacao" class="form-label">Data de Contratação:</label>
                            <input type="date" class="form-control" id="dataContratacao" name="DATA_CONTRATACAO" required>
                        </div>
                        <div class="mb-3">
                            <label for="dataVencimento" class="form-label">Data de Vencimento:</label>
                            <input type="date" class="form-control" id="dataVencimento" name="DATA_VENCIMENTO" required>
                        </div>
                        <div class="mb-3">
                            <label for="juros" class="form-label">Juros (%):</label>
                            <input type="number" class="form-control" id="juros" name="JUROS" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label for="valorPago" class="form-label">Valor Pago:</label>
                            <input type="number" class="form-control" id="valorPago" name="VALOR_PAGO" step="0.01">
                        </div>
                        <div class="mb-3">
                            <label for="statusPagamento" class="form-label">Status de Pagamento:</label>
                            <select class="form-control" id="statusPagamento" name="STATUS_PAGAMENTO" required>
                                <option value="pendente">Pendente</option>
                                <option value="pago">Pago</option>
                                <option value="atrasado">Atrasado</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success">Adicionar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

<script>
    // JavaScript to handle modal behavior
    var modal = new bootstrap.Modal(document.getElementById('myModal'), {
        keyboard: false
    });

    // Button to open the modal
    var btn = document.getElementById("openModalBtn");

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.show();
    }
</script>
<%@ include file="footer.jsp" %>
</body>
</html>