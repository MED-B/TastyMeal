
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
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
<title>AjouterOffre</title>
</head>
<body>
	<div id="countC">Nombre de commandes en attente : ${nbeComEnAtt }</div>
	<div id="counL">Nombre de commandes en cours de livraison : ${nbeComEnLivr }</div>
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
			 <a
				class="p-2 text-dark" href="Logout">deconnecter</a>





		</nav>


	</div>

	<div class="container">
	<h1>Offrir des reduction</h1>
		<div class="row">

			<div class="col-md-12 order-md-1">

				<form:form class="needs-validation" novalidate="true" method="POST"
					action="/addOffre" modelAttribute="offre" name="theform"
					id="theform">


					<div class="mb-3">

						<label for="description">Description</label>
						<div class="input-group">

							<form:input path="description" id="description" type="text"
								class="form-control" placeholder="decrire votre offre"
								required="true" />

						</div>
					</div>

					<div class="mb-3">

						<label for="discount">Discount</label>


						<form:input path="discount" type="number" class="form-control"
							id="discount" placeholder="Entrez la reduction" required="true" />

					</div>
					<div class="mb-3">

						<label for="discount">Les points nécessaires</label>


						<form:input path="points" type="number" class="form-control"
							id="points" placeholder="Entrez Les points nécessaires"
							required="true" />

					</div>
					<div class="mb-3">

						<label for="date_range">Le délai accordé pour collecter
							des points</label>


						<form:input path="date_range" type="number" class="form-control"
							id="date_range"
							placeholder="Le délai accordé pour collecter des points"
							required="true" />

					</div>

					<div class="form-group">
						<label for="exampleFormControlSelect1">Operation <span>(M:Mois,
								Y:annne, D:jour)</span></label>
						<form:select path="operation" id="operation" class="form-control">
							<option>M</option>
							<option>Y</option>
							<option>D</option>


						</form:select>
					</div>


					<div class="mb-3">

						<label for="dateF">Le délai d'expiration de l'offre</label>


						<form:input path="dateF" type="date" class="form-control"
							id="eprix" placeholder="Entrez Les points nécessaires"
							required="true" />

					</div>



					<hr class="mb-4">
					<button class="btn btn-primary btn-lg btn-block" id="submit"
						type="submit">Offrir</button>
				</form:form>
			</div>
		</div>
		<footer class="my-5 pt-5 text-muted text-center text-small">
			<p class="mb-1">&copy; 2020-2021 TastyMeal</p>
			<ul class="list-inline">
				<li class="list-inline-item"><a href="#">Privacy</a></li>
				<li class="list-inline-item"><a href="#">Terms</a></li>
				<li class="list-inline-item"><a href="#">Support</a></li>
			</ul>
		</footer>
	</div>
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
</body>
</html>