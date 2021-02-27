
<%
int resId = 0;
if (session.getAttribute("resId") != null)
	resId = (int) session.getAttribute("resId");
%>
<%
int nbeComEnAtt=0;
int nbeComEnLivr=0;
if(session.getAttribute("nbeComEnAtt")!=null)
	nbeComEnAtt=(int) session.getAttribute("nbeComEnAtt");
if(session.getAttribute("nbeComEnLivr")!=null)
	nbeComEnLivr=(int)(session.getAttribute("nbeComEnLivr"));
%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
<title>Nouveau Menu</title>
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
			<a class="p-2 text-dark" href="Offrir">Offrir des Redutions</a> <a
				class="p-2 text-dark" href="Logout">deconnecter</a>





		</nav>
			

		</div>
		<div class="container">
	<div class="row">

		<div class="col-md-12 order-md-1">

			<form:form class="needs-validation" novalidate="true" method="POST"
				action="/addMenu" modelAttribute="menu" name="theform" id="theform">

				<form:hidden path="id_restaurateur" id="is_restaurateur"
					value="${resId }" readonly="true" />
				<div class="mb-3">

					<label for="items">Repas</label>
					<div class="input-group">

						<form:input path="items" id="items" type="text"
							class="form-control" placeholder="Entrez le nom de repas "
							required="true" />

					</div>
				</div>

				<div class="mb-3">

					<label for="prix">Prix </label>


					<form:input path="prix" type="number" class="form-control"
						id="eprix" placeholder="Entrez le prix en DA" required="true" />

				</div>

				<div class="form-group">
					<label for="exampleFormControlSelect1">Categorie</label>
					<form:select path="categorie" id="categorie" class="form-control">
						<option>Pizza</option>
						<option>Burger</option>
						<option>Traditionnel</option>
						<option>Grillage</option>

					</form:select>
				</div>






				<hr class="mb-4">
				<button class="btn btn-primary btn-lg btn-block" id="submit"
					type="submit">Ajouter menus</button>
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