<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Receitas - DropMoney</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      height: 100vh;
    }
    .header {
      padding: 20px;
      background-color: #FE555D;
      color: white;
      text-align: left;
      border-bottom: 1px solid #ccc;
    }
    .title {
      font-size: 1.5em;
    }
    .container {
      flex: 1;
      padding: 20px;
      display: flex;
      flex-direction: column;
      gap: 20px;
      overflow-y: auto;
    }
    .income-item {
      background-color: #ffffff;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .footer {
      background-color: #ffffff;
      border-top: 1px solid #ccc;
      display: flex;
      justify-content: space-around;
      padding: 10px 0;
      position: fixed;
      bottom: 0;
      width: 100%;
    }
    .footer-button {
      text-align: center;
      font-size: 0.9em;
    }
    .footer-button img {
      max-width: 25px;
      display: block;
      margin: 0 auto;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="title">Receitas</div>
</div>

<div class="container">
  <div class="income-item">
    <span>Salário</span>
    <span>R$ 3.000,00</span>
  </div>
  <div class="income-item">
    <span>Venda de produtos</span>
    <span>R$ 500,00</span>
  </div>
  <div class="income-item">
    <span>Freelancer</span>
    <span>R$ 1.000,00</span>
  </div>
  <div class="income-item">
    <span>Outras fontes</span>
    <span>R$ 200,00</span>
  </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>