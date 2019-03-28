$(document).ready(function() {
  $("#login-btn").click(function () {
	  var xhttp = new XMLHttpRequest();
	    xhttp.open("POST", "http://35.185.219.131/pod/action/login", false);
	    xhttp.setRequestHeader("Content-type", "application/json");
	    xhttp.send("{\"username\":\"mbogushefsky\",\"password\":\"password1\"}");
	    var response = JSON.parse(xhttp.responseText);
	    alert(response);
  });
});