<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">

    <title>DropMoney</title>
    <style>
        body {

            font-family: 'Poppins', sans-serif;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            min-height: 100vh; /* Garante que o contêiner ocupe toda a altura da tela */
            background-color: #ffffff;
            margin: 0;
            text-align: center;
        }
        img {
            max-width: 80%;
            height: auto;
            margin-bottom: 20px;
        }
        h1 {
            font-size: 1.2em;
            color: #000000;
            margin-bottom: 30px;
        }
        .button {
            padding: 15px 30px;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            font-size: 1.1em;
            margin: 10px; /* Espaçamento entre os botões */
            width: 250px; /* Largura fixa para os botões */
        }
        .create-account {
            background-color: #FE555D;
            color: white;
        }
        .login {
            background-color: transparent;
            color: #FE555D;
            border: 2px solid #FE555D; /* Borda para o botão de login */
        }
    </style>
</head>
<body>
<div>
    <img src="assets/graphic.png" alt="Logo DropMoney">
    <h1>Esteja sempre informado sobre sua situação financeira conosco!</h1>
    <button class="button create-account" onclick="window.location.href='register.jsp'">Criar Sua Conta</button>
    <button class="button login" onclick="window.location.href='login.jsp'">Login</button>
</div>
</body>
</html>