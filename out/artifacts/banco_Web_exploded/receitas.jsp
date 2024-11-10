<%@ page import="br.com.fiap.model.Receita" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Receitas</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    /* Custom styles for the page */
    .modal-content {
      border-radius: 10px;
      padding: 20px;
    }

    .close {
      color: #aaa;
      font-size: 28px;
      font-weight: bold;
    }

    .button {
      background-color: #FE555D;
    }

    .close:hover, .close:focus {
      color: black;
      cursor: pointer;
    }
  </style>
</head>
<body class="bg-light">

<div class="container my-5">
  <h2 class="text-center mb-4">Lista de Receitas</h2>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
  <div class="alert alert-danger text-center">
    <%= errorMessage %>
  </div>
  <% } %>

  <%
    List<Receita> receitas = (List<br.com.fiap.model.Receita>) request.getAttribute("receitas");
    if (receitas != null && !receitas.isEmpty()) {
      // Ordenando a lista de receitas pela ID
      Collections.sort(receitas, new Comparator<Receita>() {
        public int compare(Receita r1, Receita r2) {
          return Long.compare(r1.getId(), r2.getId());
        }
      });
  %>
  <table class="table table-bordered table-striped">
    <thead class="thead-light">
    <tr>
      <th>ID</th>
      <th>Valor</th>
      <th>Data de Recebimento</th>
      <th>Descrição</th>
    </tr>
    </thead>
    <tbody>
    <% for (br.com.fiap.model.Receita receita : receitas) { %>
    <tr>
      <td><%= receita.getId() %></td>
      <td>R$ <%= receita.getValor() %></td>
      <td><%= receita.getDataRecebimento() %></td>
      <td><%= receita.getDescricao() %></td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } else { %>
  <p class="text-center">Nenhuma receita encontrada para o usuário.</p>
  <% } %>

  <!-- Button to trigger modal -->
  <div class="text-center">
    <button class="btn btn-primary" id="openModalBtn">Adicionar Nova Receita</button>
  </div>

  <!-- Modal -->
  <div id="myModal" class="modal" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Adicionar Nova Receita</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form action="banco-receita" method="post">
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
              <label for="data_recebimento" class="form-label">Data de Recebimento:</label>
              <input type="date" class="form-control" id="data_recebimento" name="data_recebimento" required>
            </div>
            <button type="submit" class="btn btn-success">Adicionar</button>
          </form>
        </div>
      </div>
    </div>
  </div>

</div>
<%@ include file="footer.jsp" %>

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

</body>
</html>