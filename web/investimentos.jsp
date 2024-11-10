<%@ page import="br.com.fiap.model.Investimento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Investimentos</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    /* Custom styles for the page */
    body {
      display: flex;
      flex-direction: column;
      height: 100vh; /* 100% da altura da tela */
      margin: 0;
    }

    .container {
      flex: 1; /* Faz o conteúdo ocupar o espaço restante */
      padding-bottom: 80px; /* Espaço para o footer fixo */
    }

    .footer {
      display: flex;
      justify-content: space-around; /* Distribui os itens igualmente */
      align-items: center; /* Alinha os itens verticalmente */
      background-color: #f8f9fa; /* Cor de fundo do footer */
      padding: 10px;
      position: fixed;
      bottom: 0;
      width: 100%;
      box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Sombra leve acima do footer */
    }

    .footer-button {
      text-decoration: none;
      color: #007bff;
      font-size: 14px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .footer-button i {
      font-size: 24px;
      margin-bottom: 5px;
    }

    .footer-button:hover {
      color: #0056b3;
    }

    .button {
      background-color: #FE555D;
    }

    /* Modal Customizations */
    .modal-content {
      border-radius: 10px;
      padding: 20px;
    }
  </style>
</head>
<body class="bg-light">

<div class="container my-5">
  <h2 class="text-center mb-4">Lista de Investimentos</h2>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
  <div class="alert alert-danger text-center">
    <%= errorMessage %>
  </div>
  <% } %>

  <%
    List<Investimento> investimentos = (List<br.com.fiap.model.Investimento>) request.getAttribute("investimentos");
    if (investimentos != null && !investimentos.isEmpty()) {
      // Ordenando a lista de investimentos pelo ID
      Collections.sort(investimentos, new Comparator<Investimento>() {
        public int compare(Investimento i1, Investimento i2) {
          return Long.compare(i1.getId(), i2.getId());
        }
      });
  %>
  <table class="table table-bordered table-striped">
    <thead class="thead-light">
    <tr>
      <th>ID</th>
      <th>Valor Investido</th>
      <th>Data do Investimento</th>
      <th>Taxa de Retorno (%)</th>
      <th>Valor Acumulado</th>
    </tr>
    </thead>
    <tbody>
    <% for (Investimento investimento : investimentos) { %>
    <tr>
      <td><%= investimento.getId() %></td>
      <td>R$ <%= investimento.getValorInvestido() %></td>
      <td><%= investimento.getDataInvestimento() %></td>
      <td><%= investimento.getRetornoEstimado()%> %</td>
      <td><%= investimento.getTipoInvestimento() %></td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } else { %>
  <p class="text-center">Nenhum investimento encontrado para o usuário.</p>
  <% } %>

  <!-- Button to trigger modal -->
  <div class="text-center">
    <button class="btn btn-primary" id="openModalBtn">Adicionar Novo Investimento</button>
  </div>

  <!-- Modal -->
  <div id="myModal" class="modal" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Adicionar Novo Investimento</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form action="banco-investimento" method="post">
            <input type="hidden" name="idCliente" value="<%= session.getAttribute("userId") %>" />

            <!-- Campo Valor do Investimento -->
            <div class="mb-3">
              <label for="valorInvestido" class="form-label">Valor do Investimento:</label>
              <input type="number" class="form-control" id="valorInvestido" name="VALOR_INVESTIDO" step="0.01" required>
            </div>

            <!-- Campo Data do Investimento -->
            <div class="mb-3">
              <label for="dataInvestimento" class="form-label">Data do Investimento:</label>
              <input type="date" class="form-control" id="dataInvestimento" name="DATA_INVESTIMENTO" required>
            </div>

            <!-- Campo Tipo de Investimento -->
            <div class="mb-3">
              <label for="tipoInvestimento" class="form-label">Tipo de Investimento:</label>
              <input type="text" class="form-control" id="tipoInvestimento" name="TIPO_INVESTIMENTO" required>
            </div>

            <!-- Campo Retorno Estimado -->
            <div class="mb-3">
              <label for="retornoEstimado" class="form-label">Retorno Estimado (%):</label>
              <input type="number" class="form-control" id="retornoEstimado" name="RETORNO_ESTIMADO" step="0.01" required>
            </div>

            <!-- Botão de Submissão -->
            <button type="submit" class="btn btn-success">Adicionar</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Footer -->
<div class="footer">
  <a href="home.jsp" class="footer-button">
    <i class="bi bi-house"></i> <!-- Ícone do Bootstrap -->
    Inicio
  </a>
  <a href="banco-investimento" class="footer-button">
    <i class="bi bi-wallet"></i> <!-- Ícone do Bootstrap -->
    Investimentos
  </a>
  <a href="perfil.jsp" class="footer-button">
    <i class="bi bi-person-circle"></i> <!-- Ícone do Bootstrap -->
    Conta
  </a>
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

</body>
</html>