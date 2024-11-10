<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Perfil - DropMoney</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      background-color: #f9f9f9;
      height: 100vh;
    }
    .header {
      padding: 20px;
      background-color: #ffffff;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    .profile-title {
      font-size: 1.8em;
      color: #333;
      font-weight: 600;
    }
    .profile-card {
      background-color: #FE555D;
      border-radius: 12px;
      padding: 20px;
      margin: 20px 15px;
      box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
      text-align: left;
    }
    .profile-info-title {
      font-weight: bold;
      font-size: 1.1em;
      color: white;
      margin-top: 10px;
    }
    .profile-info {
      font-size: 1.2em;
      color: white;
      margin-top: 5px;
      line-height: 1.6;
    }
    form {
      margin-top: 20px;
    }
    button {
      background-color: #ffffff;
      color: #FE555D;
      border: none;
      padding: 10px 20px;
      border-radius: 30px;
      font-size: 1em;
      cursor: pointer;
      transition: background-color 0.3s ease;
      width: 100%;
      text-align: center;
    }
    button:hover {
      background-color: #FE555D;
      color: #ffffff;
    }
  </style>
</head>
<body>

<div class="header">
  <div class="profile-title">Perfil de <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Usuário" %></div>
</div>

<div class="profile-card">
  <div class="profile-info-title">Nome</div>
  <div class="profile-info">
    <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Não disponível" %>
  </div>
  <div class="profile-info-title">ID Usuário</div>
  <div class="profile-info">
    <%= session.getAttribute("userId") != null ? session.getAttribute("userId") : "Não disponível" %>
  </div>

  <div class="profile-info-title">Email</div>
  <div class="profile-info">
    <%= session.getAttribute("userEmail") != null ? session.getAttribute("userEmail") : "Não disponível" %>
  </div>

  <!-- Botão de Logout -->
  <form action="Logout" method="get">
    <button type="submit">Sair</button>
  </form>
</div>

</body>
</html>