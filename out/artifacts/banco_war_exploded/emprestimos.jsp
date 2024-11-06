<%@ page import="br.com.fiap.model.Emprestimo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lista de Empréstimos</title>
</head>
<body>
<h1>Empréstimos Cadastrados</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Cliente ID</th>
        <th>Valor Empréstimo</th>
        <th>Data Contratação</th>
        <th>Data Vencimento</th>
        <th>Juros</th>
        <th>Valor Pago</th>
        <th>Status Pagamento</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Emprestimo> emprestimos = (List<Emprestimo>) request.getAttribute("emprestimos");

        if (emprestimos != null && !emprestimos.isEmpty()) {
            for (Emprestimo emprestimo : emprestimos) {
    %>
    <tr>
        <td><%= emprestimo.getId() %></td>
        <td><%= emprestimo.getIdCliente() %></td>
        <td><%= emprestimo.getValorEmprestimo() %></td>
        <td><%= emprestimo.getDataContratacao() %></td>
        <td><%= emprestimo.getDataVencimento() %></td>
        <td><%= emprestimo.getJuros() %></td>
        <td><%= emprestimo.getValorPago() %></td>
        <td><%= emprestimo.getStatusPagamento() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="8">Nenhum empréstimo encontrado.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>