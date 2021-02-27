
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
int nbeComEnAtt=0;
int nbeComEnLivr=0;
if(session.getAttribute("nbeComEnAtt")!=null)
	nbeComEnAtt=(int) session.getAttribute("nbeComEnAtt");
if(session.getAttribute("nbeComEnLivr")!=null)
	nbeComEnLivr=(int)(session.getAttribute("nbeComEnLivr"));
%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/EspaceRetaurateur.css">
<title>EspaceRestaurateur</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
</head>
<body>



	<div id="countC">Nombre de commandes en attente : ${nbeComEnAtt }</div>
	<div id="counL">Nombre de commandes en cours de livraison : ${nbeComEnLivr }</div>

	<div
		class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow ">

		<h5 class="my-0 mr-md-auto font-weight-normal">
			<img style="width: 80px;" src="pics/Tasty.png" id="logo">
		</h5>
		<nav class="my-2 my-md-0 mr-md-3">
			<a class="p-2 text-dark" href="/">Accuile</a> <a
				class="p-2 text-dark dropdown-toggle " data-toggle="dropdown"
				href="#">Proprietes</a>
			<div class="dropdown-menu p-2 text-dark">
				<a class="dropdown-item" href="consulterMenusRes">Mes Menus</a> <a
					class="dropdown-item" href="consulterCommande">Mes Commandes</a> <a
					class="dropdown-item" href="consulterOffres">Mes offres</a>
			</div>
			<a class="p-2 text-dark" href="Offrir">Offrir des Redutions</a> <a
				class="p-2 text-dark" href="Logout">deconnecter</a>





		</nav>


	</div>
	<div class="container">
		<h2>Liste des commande en attente</h2>
		<p>actualiser l'etat de commande.</p>

		<c:forEach items="${list}" var="commandeSpec">


			<div id="CommandeInfo">
				<div class="card">
					<div class="card-body">
						<ul>

							<form:form method="POST" action="/actualiserCommande"
								modelAttribute="commandeSpec">
								<li>numero de Commande: <form:input path="id_commande"
										id="id_commande" value="${commandeSpec.id_commande }"
										readonly="true" /></li>
								<li>Email de client :<form:input path="email" id="email"
										value="${commandeSpec.email }" readonly="true" /></li>
								<li>Adresse de livraison :<form:input path="adress"
										id="adress" value="${commandeSpec.adress }" readonly="true" /></li>
								<li>Repas : <form:input path="items" id="items"
										value="${commandeSpec.items }" readonly="true" /></li>
								<li>Prix : <form:input path="prix" id="prix"
										value="${commandeSpec.prix }" readonly="true" /></li>
								<li>Etat : <form:input path="state" id="state"
										value="${commandeSpec.state }" readonly="true" /></li>
								<li><input type="submit" class="btn btn-primary"
									value="Actualiser"></li>
							</form:form>

						</ul>
					</div>
				</div>
			</div>

		</c:forEach>


	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js">>
		Holder.addTheme('thumb', {
			bg : '#55595c',
			fg : '#eceeef',
			text : 'Thumbnail'
		});
	</script>
</body>
</html>