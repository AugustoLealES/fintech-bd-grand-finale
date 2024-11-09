<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login - DropMoney</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 100vh;
      background-color: #ffffff;
      margin: 0;
      text-align: center;
      padding: 20px;
    }
    h1 {
      font-size: 1.8em;
      color: black;
      margin-bottom: 10px;
    }
    .input-field {
      width: 100%;
      max-width: 300px;
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 1em;
    }
    .button {
      padding: 15px 30px;
      border: none;
      border-radius: 25px;
      cursor: pointer;
      font-size: 1.1em;
      margin: 10px;
      width: 100%;
      max-width: 250px;
    }
    .login {
      background-color: #FE555D;
      color: white;
    }
    .back {
      background-color: transparent;
      color: #FE555D;
      border: 2px solid #FE555D;
    }
    .error-message {
      color: #FF0000;
      font-size: 0.9em;
      margin-top: 5px;
    }
  </style>
</head>
<body>
<h1>Login</h1>

<%
  // Verifica se há uma mensagem de erro na sessão
  String errorMessage = (String) session.getAttribute("errorMessage");
  if (errorMessage != null) {
%>
<div class="error-message"><%= errorMessage %></div>
<%
    // Limpa a mensagem de erro após exibi-la
    session.removeAttribute("errorMessage");
  }
%>

<form action="LoginServlet" method="post">
  <input type="text" name="email" class="input-field" placeholder="Email" required>
  <input type="password" name="senha" class="input-field" placeholder="Senha" required>
  <button type="submit" class="button login">Entrar</button>
</form>
<button class="button back" onclick="window.location.href='index.jsp'">Voltar</button>
</body>
</html>