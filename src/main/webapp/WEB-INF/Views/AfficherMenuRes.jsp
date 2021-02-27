
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
<link rel="stylesheet" href="css/AfficheMenus.css">
<title>Mes Menus</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
</head>
<body>
	
		<div id="countC">Nombre de commandes en attente : ${nbeComEnAtt }</div>
        <div id="counL">Nombre de commandes en cours de livraison :${nbeComEnLivr }</div>
		<div
			class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">

			<h5 class="my-0 mr-md-auto font-weight-normal">
				<img style="width: 80px;" src="pics/Tasty.png" id="logo">
			</h5>
			<nav class="my-2 my-md-0 mr-md-3">
			<a class="p-2 text-dark" href="/EspaceRestaurateur">Accuile</a> <a
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



			<div class="row">
				<div class="col-md-4 order-md-2 mb-4 ">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="text-muted">Consulter les menus par :</span>

					</h4>
					<ul class="list-group mb-3">
					<li class="list-group-item d-flex justify-content-between"><a class="p-2 text-dark" href="ajouterMenu">Ajouter Menu</a></li>
						<li class="list-group-item d-flex justify-content-between"><a
							class="p-2 text-dark dropdown-toggle" href="#" id="categories"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> Categorie </a>
							<div class="dropdown-menu" aria-labelledby="categories">


								<a class="dropdown-item" href="consulterMenusRes">tous les menus</a> <a
									class="dropdown-item" href="pizzaRes">Pizza</a> <a
									class="dropdown-item" href="burgerRes">Burgers</a> <a
									class="dropdown-item" href="traditionnelRes">Traditionnel</a> <a
									class="dropdown-item" href="grillageRes">Grillages</a>

							</div></li>
					</ul>
 
					<form:form class="card p-2" action="consulterPrix" method="POST" modelAttribute="menu">
						<div class="input-group">
							<form:input type="number" class="form-control" path="prix" />
							<div class="input-group-append">
								<button type="submit" class="btn btn-secondary">Consulter</button>
							</div>
						</div>
					</form:form>
				</div>
				<div class="col-md-8 order-md-1">
					<h1>Menus List : ${categorie }</h1>
					<c:forEach items="${list}" var="menu">
						<div class="col-sm-12">
							<div class="card">
								<div class="card-body">
									<form:form method="POST" action="/ChangerMenu"
										modelAttribute="menu">
										<h5 class="card-title">
											<form:input path="code_menu" id="code_menu"
												value="${menu.code_menu }" readonly="true" /><form:hidden path="id_restaurateur" id="is_restaurateur"
											value="${menu.id_restaurateur }" readonly="true" /><form:hidden path="categorie" id="categorie"
											value="${menu.categorie }" readonly="true" />
										</h5>
										
										<p class="card-text ">
										<p>	<form:input path="items" id="items" value="${menu.items }"
												readonly="true" /></p><br><p> <span>Prix :</span>
												<form:input path="prix" id="prix" value="${menu.prix }"
												readonly="true" /> <span>Da </span></p><input class="btn btn-danger" type="submit"
												value="Changer "  />
											
										</p>


									</form:form>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>

			</div>
		</div>
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>