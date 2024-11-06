<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - DropMoney</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
 <!-- Bootstrap CSS -->
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
            background-color: #ffffff;
            text-align: left;
            border-bottom: 1px solid #ccc;
        }
        .welcome {
            font-size: 1.5em;
            color: black;
        }
        .balance-card {
            background-color: #FE555D;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: left;
        }
        .balance-title {
            font-weight: bold;
            font-size: 1.2em;
            color: white;
        }
        .balance-title2 {
            font-weight: normal;
            font-size: 1.2em;
            color: white;
        }
        .balance-amount {
            font-weight: 500;
            font-size: 1.1em;
            color: white;
            margin-top: 5px;
        }
        .button-container {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .button {
            background-color: #FE555D;
            color: white;
            border-radius: 15px;
            width: 100px;
            height: 100px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin: 0 10px;
            text-align: center;
            font-size: 0.9em;
            text-decoration: none; /* Remove underline */
        }
        .button i {
            font-size: 2em; /* Ajusta o tamanho do ícone */
            margin-bottom: 5px; /* Espaçamento entre ícone e texto */
        }
        .gray-box {
            background-color: #f5f5f5;
            border-radius: 10px;
            padding: 15px;
            margin: 10px;
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
            font-size: 0.9em;<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home - DropMoney</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
        <!-- Bootstrap CSS -->
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
                background-color: #ffffff;
                text-align: left;
                border-bottom: 1px solid #ccc;
            }
            .welcome {
                font-size: 1.5em;
                color: black;
            }
            .balance-card {
                background-color: #FE555D;
                border-radius: 10px;
                padding: 20px;
                margin: 20px 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                text-align: left;
            }
            .balance-title {
                font-weight: bold;
                font-size: 1.2em;
                color: white;
            }
            .balance-title2 {
                font-weight: normal;
                font-size: 1.2em;
                color: white;
            }
            .balance-amount {
                font-weight: 500;
                font-size: 1.1em;
                color: white;
                margin-top: 5px;
            }
            .button-container {
                display: flex;
                justify-content: center;
                margin: 20px 0;
            }
            .button {
                background-color: #FE555D;
                color: white;
                border-radius: 15px;
                width: 100px;
                height: 100px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                margin: 0 10px;
                text-align: center;
                font-size: 0.9em;
                text-decoration: none; /* Remove underline */
            }
            .button i {
                font-size: 2em; /* Ajusta o tamanho do ícone */
                margin-bottom: 5px; /* Espaçamento entre ícone e texto */
            }
            .gray-box {
                background-color: #f5f5f5;
                border-radius: 10px;
                padding: 15px;
                margin: 10px;
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
            .footer-button i {
                font-size: 1.5em; /* Ajusta o tamanho do ícone no footer */
                display: block;
                margin: 0 auto;
            }
            .content {
                flex: 1;
                padding-bottom: 60px;
            }}
    </style>
</head>
<body>
<div class="header">
    <div class="welcome">Bem-vindo, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Usuário" %>!</div>

</div>

<div class="content">
    <div class="balance-card">
        <div class="balance-title">Conta</div>
        <div class="balance-title2">Saldo</div>
        <div class="balance-amount">
            R$ <%= session.getAttribute("userBalance") != null ? String.format("%.2f", (Double) session.getAttribute("userBalance")) : "0.00" %>
        </div>
    </div>

    <div class="button-container">
        <a href="investimentos.jsp" class="button">
            <i class="bi bi-bar-chart-line"></i> <!-- Ícone do Bootstrap -->
            Tela 1
        </a>
        <a href="receitas.jsp" class="button">
            <i class="bi bi-wallet2"></i> <!-- Ícone do Bootstrap -->
            Tela 2
        </a>
        <a href="emprestimos.jsp" class="button">
            <i class="bi bi-credit-card"></i> <!-- Ícone do Bootstrap -->
            Tela 3
        </a>
    </div>

    <div class="gray-box">
        <span>Label 1</span>
        <i class="bi bi-check-circle"></i> <!-- Ícone do Bootstrap -->
    </div>
    <div class="gray-box">
        <span>Label 2</span>
        <i class="bi bi-exclamation-circle"></i> <!-- Ícone do Bootstrap -->
    </div>
    <div class="gray-box">
        <span>Label 3</span>
        <i class="bi bi-info-circle"></i> <!-- Ícone do Bootstrap -->
    </div>
    <div class="gray-box">
        <span>Label 4</span>
        <i class="bi bi-question-circle"></i> <!-- Ícone do Bootstrap -->
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
