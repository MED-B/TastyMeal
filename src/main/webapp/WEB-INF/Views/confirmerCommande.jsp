<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="css/Authentifier.css">
<title>ConfirmationCommande</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script>
</head>
<body>
	
	
	
<div class="wrapper fadeInDown">
  <div id="formContent">
    <!-- Tabs Titles -->

    <!-- Icon -->
    <div class="fadeIn first">
      <img src="pics/Tasty.png" width="50px" id="icon" alt="User Icon" />
    </div>

    <!-- Login Form -->
    <form:form method="POST" action="/addCommande"
		modelAttribute="client">
      <form:input type="email" id="login" path="email" class="fadeIn second" name="login" placeholder="E-mai"/>
      <form:input type="password" id="password" path="pass" class="fadeIn third" name="login" placeholder="Mot de passe"/>
      <input type="submit" class="fadeIn fourth" value="S'authentifier">
    </form:form>

    <!-- Remind Passowrd -->
    <div id="formFooter">
      <a class="underlineHover" href="#">Oublier le mot de passe?</a>
      <p></p>
      <a class="underlineHover" href="abonner">Creer un compte</a>
      <p></p>
      <a class="underlineHover" href="addCommandeGuest">Commander seulement(invité)</a>
    </div>

  </div>
</div>
</body>
</html>