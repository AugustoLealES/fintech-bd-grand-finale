<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"> <!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<style>
    /* Define o layout da página para garantir que o footer fique fixado embaixo */
    body {
        display: flex;
        flex-direction: column;
        height: 100vh; /* 100% da altura da tela */
        margin: 0;
    }

    .content {
        flex: 1; /* Faz o conteúdo ocupar o espaço restante, permitindo que o footer fique fixado */
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
</style>

<div class="content">
    <!-- Aqui vai o conteúdo principal da página -->
</div>

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