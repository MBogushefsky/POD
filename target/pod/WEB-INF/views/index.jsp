<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Plaid Walkthrough Example</title>
  <link rel="shortcut icon" href="resources/application/img/favicon.ico">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="resources/jquery/jquery.min.js"></script>
  <script src="resources/plaid/js/link-initialize.js"></script>
  <script src="resources/bootstrap/js/bootstrap.min.js"></script>
  <script src="resources/flat-ui/js/flat-ui.min.js"></script>
  <script src="resources/application/js/main-script.js"></script>
  <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="resources/flat-ui/css/flat-ui.min.css">
  <link rel="stylesheet" type="text/css" href="resources/application/css/style.css">
</head>
<body>
  <div id="banner">
    <h1>Welcome to Pod ${message}</h1>
    <p id="intro">
      This is an example application that walks through integrating Plaid Link using the API to retrieve Auth and Transaction data.
    </p>
    <p id="steps">
      Great - you just created an Item! The server was successfully able to exchange the public_token for an access_token.
      Below are a few options - you can get account data, retrieve information about the Item itself, or pull transaction data.
    </p>
  </div>

  <div id="container">
    <p>
      Click the button below to open your user settings.
    </p>
    <button id="settings-btn">Settings</button>
    <br/><br/><br/><br/>
    <p>
      Click the button below to open to get a list of all your accounts and balances.
    </p>
    <button id="get-acc-btn">Get Accounts</button>
    <br/><br/><br/><br/>
    <p>
      Click the button below to all accounts.
    </p>
    <button id="update-acc-btn">Update Accounts</button>
    <br/><br/><br/><br/>
    <p>
      Click the button below to open a list of Institutions - after you select one,
      you'll be guided through an authentication process. The public_token will be passed
      back to the example server, which will then exchange it for an access token and log it
      to your console.
    </p>
    <button id="link-btn">Link Accounts</button>
  </div>

  <div id="app">
    <div class="box">
      <button id="get-accounts-btn">Get Accounts</button>
      <div id="get-accounts-data"></div>
    </div>

    <div class="box">
      <button id="get-item-btn">Get Item</button>
      <div id="get-item-data"></div>
    </div>

    <div class="box">
      <button id="get-transactions-btn">Get Transactions</button>
      <div id="get-transactions-data"></div>
    </div>
  </div>
</body>
</html>
