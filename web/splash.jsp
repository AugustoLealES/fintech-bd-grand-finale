<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

  <title>Carregando...</title>
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      background-color: #ffffff;
      margin: 0;
      text-align: center;
    }
    img {
      max-width: 80%;
      height: auto;
    }
    h1 {
      margin-top: 20px;
      font-size: 2em;
      color: #000000;
    }
  </style>
  <script>

    setTimeout(function() {
      window.location.href = 'index.jsp';
    }, 3000);
  </script>
</head>
<body>
<div>
  <img src="assets/pig.png" alt="Logo DropMoney">
  <h1>DropMoney</h1>
</body>
</html>