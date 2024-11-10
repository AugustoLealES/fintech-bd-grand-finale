<%@ page import="br.com.fiap.model.Despesa" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Despesas</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .modal-content {
            border-radius: 10px;
            padding: 20px;
        }

        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover, .close:focus {
            color: black;
            cursor: pointer;
        }
        .button {
            background-color: #FE555D;
        }
    </style>
</head>
<body class="bg-light">

<div class="container my-5">
    <h2 class="text-center mb-4">Lista de Despesas</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="alert alert-danger text-center">
        <%= errorMessage %>
    </div>
    <% } %>

    <%
        List<Despesa> despesas = (List<br.com.fiap.model.Despesa>) request.getAttribute("despesas");
        if (despesas != null && !despesas.isEmpty()) {
            // Ordenando a lista de despesas pela ID
            Collections.sort(despesas, new Comparator<Despesa>() {
                public int compare(Despesa d1, Despesa d2) {
                    return Long.compare(d1.getId(), d2.getId());
                }
            });
    %>
    <table class="table table-bordered table-striped">
        <thead class="thead-light">
        <tr>
            <th>ID</th>
            <th>Valor</th>
            <th>Data de Pagamento</th>
            <th>Descrição</th>
        </tr>
        </thead>
        <tbody>
        <% for (br.com.fiap.model.Despesa despesa : despesas) { %>
        <tr>
            <td><%= despesa.getId() %></td>
            <td>R$ <%= despesa.getValor() %></td>
            <td><%= despesa.getDataPagamento() %></td>
            <td><%= despesa.getDescricao() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="text-center">Nenhuma despesa encontrada para o usuário.</p>
    <% } %>

    <!-- Button to trigger modal -->
    <div class="text-center">
        <button class="btn btn-primary" id="openModalBtn">Adicionar Nova Despesa</button>
    </div>

    <!-- Modal -->
    <div id="myModal" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Adicionar Nova Despesa</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="banco-despesa" method="post">
                        <input type="hidden" name="idCliente" value="<%= session.getAttribute("userId") %>" />
                        <div class="mb-3">
                            <label for="valor" class="form-label">Valor:</label>
                            <input type="number" class="form-control" id="valor" name="valor" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label for="descricao" class="form-label">Descrição:</label>
                            <input type="text" class="form-control" id="descricao" name="descricao" required>
                        </div>
                        <div class="mb-3">
                            <label for="data_pagamento" class="form-label">Data de Pagamento:</label>
                            <input type="date" class="form-control" id="data_pagamento" name="data_pagamento" required>
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