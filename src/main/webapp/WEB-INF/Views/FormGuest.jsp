<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String error = "";
%>
<%
int nbeComEnAtt = 0;
int nbeComEnLivr = 0;
if (session.getAttribute("nbeComEnAtt") != null)
	nbeComEnAtt = (int) session.getAttribute("nbeComEnAtt");
if (session.getAttribute("nbeComEnLivr") != null)
	nbeComEnLivr = (int) (session.getAttribute("nbeComEnLivr"));
%>
<%
if ((session.getAttribute("done") != null) && ((boolean) session.getAttribute("done") == false)) {
	error.equalsIgnoreCase((String) (session.getAttribute("error")));

}
%>

<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>CommanderInvité</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
</head>
<body>

	<div class="contianer">
		<div id="countC">Nombre de commandes en attente : ${nbeComEnAtt }</div>



		<div id="counL">Nombre de commandes en cours de livraison :
			${nbeComEnLivr }</div>
		<div
			class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">

			<h5 class="my-0 mr-md-auto font-weight-normal">
				<img style="width: 80px;" src="pics/Tasty.png" id="logo">
			</h5>
			<nav class="my-2 my-md-0 mr-md-3">
				<a class="p-2 text-dark" href="/EspaceClient">Accuille</a> <a
					class="p-2 text-dark" href="consulterMenus">Menus</a> <a
					class="p-2 text-dark" href="suivreCommande">Ma Commande</a>



				<c:choose>
					<c:when test="${state !=null  }">
						<a class="p-2 text-dark dropdown-toggle" href="#"
							id="navbarDropdown" role="button" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"> Gerer Compte </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="#">Voir Profile</a>


							<div class="dropdown-divider"></div>



							<a class="dropdown-item" href="Logout">deconnecter</a>
						</div>
					</c:when>
				</c:choose>

			</nav>
			<c:choose>
				<c:when test="${state!=null }">

					<a class="btn btn-primary" href="abonner">Abonné</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-outline-primary" href="abonner">Abonner</a>
				</c:otherwise>
			</c:choose>

		</div>


		<div class="container">
			<div class="py-5 text-center">
				<img class="d-block mx-auto mb-4" src="pics/Tasty.png" alt=""
					width="72">
				<h2>Commander sans abonnement</h2>
				<p class="lead">si vous commandez sans vous abonner, vous ne
					pourrez pas obtenir de réductions.</p>
			</div>

			<div class="row">

				<div class="col-md-12 order-md-1">

					<form:form class="needs-validation" novalidate="true" method="POST"
						action="/confirmerCommandeGuest" modelAttribute="client"
						name="theform" id="theform">



						<div class="mb-3">

							<label for="email">Email <span class="text-muted">(adresse
									de livraison)</span> <c:if test="${error != null }">
									<p style="color: tomato;">${error }</p>
								</c:if></label>
							<form:input path="email" type="email" class="form-control"
								id="email" placeholder="you@example.com" required="true" />
							<div class="invalid-feedback">Please enter a valid email
								address for shipping updates.</div>
						</div>

						<div class="mb-3">
							<label for="address">Address</label>
							<form:input path="adress" type="text" class="form-control"
								id="address" placeholder="1234 Main St" required="true" />
							<div class="invalid-feedback">Please enter your shipping
								address.</div>
						</div>
						<div class="mb-3">
							<label for="tel">Numéro de Téléphone (FORMAT:
								01-23-45-67-89)</label>
							<form:input path="tel" type="tel" class="form-control" id="tel"
								placeholder="0#-##-##-##-##"
								pattern="[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}"
								required="true" />
							<div class="invalid-feedback">Please enter your shipping
								address.</div>
						</div>




						<hr class="mb-4">
						<button class="btn btn-primary btn-lg btn-block" id="submit"
							type="submit">Commander</button>
					</form:form>
				</div>
			</div>
			<footer class="my-5 pt-5 text-muted text-center text-small">
				<p class="mb-1">&copy; 2021 M.B</p>
				<ul class="list-inline">
					<li class="list-inline-item"><a href="#">Privacy</a></li>
					<li class="list-inline-item"><a href="#">Terms</a></li>
					<li class="list-inline-item"><a href="#">Support</a></li>
				</ul>
			</footer>
		</div>
	</div>
	<script>
		var check = function() {
			if (document.getElementById('pass').value == document
					.getElementById('confipass').value) {
				document.getElementById('message').style.color = 'green';
				document.getElementById('message').innerHTML = 'matching';

			} else {
				document.getElementById('message').style.color = 'red';
				document.getElementById('message').innerHTML = 'not matching';

			}
		}
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">
		> Holder.addTheme('thumb', {
			bg : '#55595c',
			fg : '#eceeef',
			text : 'Thumbnail'
		});
	</script>


	<script>
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict';

			window.addEventListener('load',
					function() {
						// Fetch all the forms we want to apply custom Bootstrap validation styles to
						var forms = document
								.getElementsByClassName('needs-validation');

						// Loop over them and prevent submission
						var validation = Array.prototype.filter.call(forms,
								function(form) {
									form.addEventListener('submit', function(
											event) {
										if (form.checkValidity() === false) {
											event.preventDefault();
											event.stopPropagation();
										}
										form.classList.add('was-validated');
									}, false);
								});
					}, false);
		})();
	</script>
</body>
</html>