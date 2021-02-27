<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="css/Authentifier.css">
<title>Authentification</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
<script type="text/javascript"> 
        window.history.forward(); 
        function noBack() { 
            window.history.forward(); 
        } 
    </script>
</head>
<body >



	<div class="wrapper fadeInDown">
		<div id="formContent">
			<!-- Tabs Titles -->

			<!-- Icon -->
			<div class="fadeIn first">
				<img src="pics/Tasty.png" width="50px" id="icon" alt="User Icon" />
			</div>

			<!-- Login Form -->
			<form:form method="POST" action="/authentifier" modelAttribute="client">
				<form:input type="email" id="login" path="email" class="fadeIn second"
					name="login" placeholder="E-mail" />
				<form:input type="password" id="password" path="pass"
					class="fadeIn third" name="login" placeholder="Mot de passe" />
				<input type="submit" class="fadeIn fourth" value="S'authentifier">
			</form:form>

			<!-- Remind Passowrd -->
			<div id="formFooter">
				<a class="underlineHover" href="#" onclick="document.getElementById('alert').style.display ='inline';">Oublier le mot de
					passe?</a>
				<p></p>
				<a class="underlineHover" href="abonner">Creer un compte</a>

			</div>

			
		</div>
	</div>
	
			<div style="position:fixed;top:0;left:0; display:none;" id="alert" class="alert alert-info alert-dismissible fade show"
				role="alert">
				we sent a message to your email to confirme or recreate your
				password__             
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">Ok</span>
				</button>
			</div>
		
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">>
		Holder.addTheme('thumb', {
			bg : '#55595c',
			fg : '#eceeef',
			text : 'Thumbnail'
		});
	</script>
	
</body>
</html>