<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.fiap.dao.ClienteDao, br.com.fiap.model.Cliente" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Criar Conta - DropMoney</title>
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
      font-size: 2em;
      color: black;
      margin-bottom: 10px;
    }
    h2 {
      font-size: 1.2em;
      color: black;
      margin-bottom: 30px;
    }
    .input-field {
      width: 100%;
      max-width: 350px;
      padding: 12px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 1em;
      box-sizing: border-box;
    }
    .button {
      padding: 15px 30px;
      border: none;
      border-radius: 25px;
      cursor: pointer;
      font-size: 1.1em;
      margin: 15px 0;
      width: 100%;
      max-width: 250px;
      box-sizing: border-box;
    }
    .create-account {
      background-color: #FE555D;
      color: white;
    }
    .back {
      background-color: transparent;
      color: #FE555D;
      border: 2px solid #FE555D;
    }
    form {
      width: 100%;
      max-width: 400px;
      margin: 0 auto;
    }
    label {
      font-size: 1em;
      color: #333;
      text-align: left;
      margin: 5px 0;
      display: block;
    }
  </style>
  <script>
    function validarCPF(cpf) {
      const cpfSemMascara = cpf.replace(/[^\d]/g, '');
      return cpfSemMascara.length === 11;
    }

    function validarFormulario() {
      const cpf = document.querySelector('input[name="cpf"]').value;
      if (!validarCPF(cpf)) {
        alert("CPF inválido. Deve conter 11 dígitos.");
        return false;
      }
      return true;
    }
  </script>
</head>
<body>

<h1>Criar Conta</h1>
<h2>Invista e multiplique sua renda agora!</h2>

<form action="banco-cliente" method="post" onsubmit="return validarFormulario();">
  <label for="nome">Nome</label>
  <input type="text" id="nome" name="nome" class="input-field" placeholder="Nome" required>

  <label for="email">Email</label>
  <input type="email" id="email" name="email" class="input-field" placeholder="Email" required>

  <label for="senha">Senha</label>
  <input type="password" id="senha" name="senha" class="input-field" placeholder="Senha" required>

  <label for="data_nascimento">Data de Nascimento</label>
  <input type="date" id="data_nascimento" name="dataNascimento" placeholder="AAAA-MM-DD" class="input-field" required>

  <label for="cpf">CPF</label>
  <input type="text" id="cpf" name="cpf" class="input-field" placeholder="CPF" required>

  <button type="submit" class="button create-account">Criar Conta</button>
</form>

<button class="button back" onclick="window.location.href='index.jsp'">Voltar</button>

</body>
</html>