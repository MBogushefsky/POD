<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Pod Login</title>
  <link rel="shortcut icon" href="resources/application/img/favicon.ico">
  <link rel="stylesheet" type="text/css" href="style.css">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="resources/jquery/jquery.min.js"></script>
  <script src="resources/bootstrap/js/bootstrap.min.js"></script>
  <script src="resources/application/js/login-script.js"></script>
  <link rel="stylesheet" type="text/css" href="resources/application/css/style.css">
  <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css">
</head>
<body>
  <div id="banner">
    <h1>Login</h1>
    <p id="intro">
       ${message}
      This is an example application that walks through integrating Plaid Link using the API to retrieve Auth and Transaction data.
    </p>
    <p id="steps">
      Great - you just created an Item! The server was successfully able to exchange the public_token for an access_token.
      Below are a few options - you can get account data, retrieve information about the Item itself, or pull transaction data.
    </p>
  </div>

  <div id="container">
    <table id="login-table">
      <tr>
        <td class="medium-text">Username: </td>
        <td><input type="text" id="usernameInput"></input></td>
      </tr>
      <tr>
        <td class="medium-text">Password: </td>
        <td><input type="password" id="passwordInput" ></td>
      </tr>
      <tr>
        <td colspan="2"><br/><button id="login-btn" style="width: 100%;">Login</button></td>
      </tr>
    </table>
    <br/>
  </div>
</body>
</html>
