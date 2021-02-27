
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
<title>MesOffres</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
</head>
<body>



	<div id="countC">Nombre de commandes en attente : ${nbeComEnAtt }</div>
	<div id="counL">Nombre de commandes en cours de livraison :${nbeComEnLivr }</div>

	<div
		class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow ">

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
		<div class="card-deck mb-3 text-center">


			<c:forEach items="${list}" var="offre">

				<!--  debut de carte -->
				<div class="card mb-3 box-shadow">
					
						<div class="card-header">
							<h4 class="my-0 font-weight-normal">
								${offre.id }
								
									
							</h4>
						</div>
						<div class="card-body">
							<p>${offre.description }</p>
							<p>
								L'offre se termine en ${offre.dateF }
							</p>
							<p>
								<strong>Conditions:</strong> Obtenir de ${offre.points } points dans ${offre.date_range }
								<c:choose>
									<c:when test="${offre.operation=='M' }"> Mois</c:when>
									<c:when test="${offre.operation=='D' }"> Jour</c:when>
									<c:otherwise> Annee</c:otherwise>
								</c:choose>
							</p>
							<ul>
								<li style="list-style:none;">Reduction de : ${offre.discount } %</li>
								
							</ul>
							
						</div>
					
				</div>

				<!-- fin de carte -->
			</c:forEach>

		</div>


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