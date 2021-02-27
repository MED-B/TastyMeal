
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String state = "";
boolean signin = false;
String name = "";
boolean promo = false;
boolean promoWanted = false;
int numberO = 0;

String messageAccepete="";

%>
<%
int nbeComEnAtt=0;
int nbeComEnLivr=0;
if(session.getAttribute("nbeComEnAtt")!=null)
	nbeComEnAtt=(int) session.getAttribute("nbeComEnAtt");
if(session.getAttribute("nbeComEnLivr")!=null)
	nbeComEnLivr=(int)(session.getAttribute("nbeComEnLivr"));
%>
<%
if(session.getAttribute("messageAccepete")!=null)
	messageAccepete=(String)(session.getAttribute("messageAccepete"));
if ((session.getAttribute("client") != null)) {
	state.equalsIgnoreCase((String) session.getAttribute("state"));
	signin = true;
}
if (session.getAttribute("numberO") != null) {
	numberO = (int) session.getAttribute("numberO");
}
if (session.getAttribute("name") != null)
	name.equalsIgnoreCase(session.getAttribute("name").toString());

if (session.getAttribute("promoWanted") != null)
	promoWanted = (boolean) session.getAttribute("promoWanted");
if (session.getAttribute("promo") != null)
	promo = (boolean) session.getAttribute("promo");

%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/EspaceClient.css">
<title>EspaceClient</title>
<link rel="icon" href="pics/Tasty.png" type="image/icon type">
</head>
<body>

<script>
history.pushState(null,null,location.href);
window.onpopstate=function(){
  history.go(1);
};
</script>

     <div id="countC">Nombre de commandes en attente :  ${nbeComEnAtt }</div>
    <div id="counL">Nombre de commandes en cours de livraison : ${nbeComEnLivr }</div>
	
	<div
		class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow " >
       
		<h5 class="my-0 mr-md-auto font-weight-normal">
			<img style="width: 80px;" src="pics/Tasty.png" id="logo">
		</h5>
		<nav class="my-2 my-md-0 mr-md-3">
			<a class="p-2 text-dark" href="/">Accuile</a> <a
				class="p-2 text-dark" href="consulterMenus">Menus</a> <a
				class="p-2 text-dark" href="suivreCommande">Ma Commande</a> 
			

				<c:choose>
					<c:when test="${state !=null }">
						<a class="dp-2 text-dark" href="Logout">deconnecter</a>
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
	<div class="contianer">
	
	
	<c:choose>
		<c:when test='${state !=null }'>


			<div class="alert alert-primary alert-dismissible fade show"
				role="alert">
				Votre commande est : ${state }
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<br />
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>
	
		
		<c:choose>
		<c:when test='${ messageAccepete!= null}'>


			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<%= session.getAttribute("messageAccepete") %>
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<br />
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>
		



	<div
		class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
		<h1 class="display-4">Promotion!</h1>
		<p class="lead">vérifiez s'il y a des promotions que vous pouvez obtenir, vérifiez les conditions si elles sont valides pour vous cliquez sur obtenir de promotion.</p>
	</div>

	<div class="container">

		<div class="card-deck mb-3 text-center">


			<c:forEach items="${list}" var="offre">

				<!--  debut de carte -->
				<div class="card mb-3 box-shadow">
					<form:form method="POST" action="/reclamerPromo"
						modelAttribute="offre">
						<div class="card-header">
							<h4 class="my-0 font-weight-normal">
								Offre de restaurateur
								<form:hidden path="id_restaurateur" id="id_restaurateur"
									value="${offre.id_restaurateur }" readonly="true" />
									
							</h4>
						</div>
						<div class="card-body">
							<p>${offre.description }</p>
							<p>
								L'offre se termine en
								<form:input path="dateF" id="dateF" value="${offre.dateF }"
									readonly="true" />
							</p>
							<p>
								<strong>Conditions:</strong> Obtenir de
								<form:input path="points" id="points" value="${offre.points }"
									readonly="true" /> points
								dans
								<form:input path="date_range" id="date_range"
									value="${offre.date_range }" readonly="true" />
								<c:choose>
									<c:when test="${offre.operation=='M' }"> Mois</c:when>
									<c:when test="${offre.operation=='D' }"> Jour</c:when>
									<c:otherwise> Annee</c:otherwise>
								</c:choose>
							</p>
							<ul>
								<li style="list-style:none;">Reduction de <form:input path="discount" id="discount"
										value="${offre.discount }" readonly="true" />%</li>
								<form:hidden path="operation" id="operation"
									value="${offre.operation}" readonly="true" />
									<form:hidden path="description" id="description"
									value="${offre.description}" readonly="true" />
								<form:hidden path="dateD" id="dateD" value="${offre.dateD}"
									readonly="true" />
								<form:hidden path="id" id="id" value="${offre.id}"
									readonly="true" />
							</ul>
							<c:choose>
							
								<c:when test="${ promoWanted==true}">
									<c:choose>
										<c:when test="${promo==true }">
											<c:choose>
												<c:when test="${numberO == offre.id }">
													<input id="cardbrns" type="submit"
														class="btn btn-lg btn-block btn-outline-success"
														value="C'est bon" disabled>
												</c:when>
												<c:when test="${numberO != offre.id }">
													<input id="cardbrns" type="submit"
														class="btn btn-lg btn-block btn-outline-primary"
														value="Obtenir de promotion">

												</c:when>
											</c:choose>




										</c:when>
										<c:when test="${ promo==false  }">
											<c:choose>
												<c:when test="${numberO == offre.id }">
													<input id="cardbrns" type="submit"
														class="btn btn-lg btn-block btn-outline-danger"
														value="Invalide">
												</c:when>
												<c:when test="${numberO != offre.id }">
													<input id="cardbrns" type="submit"
														class="btn btn-lg btn-block btn-outline-primary"
														value="Obtenir de promotion">
												</c:when>
											</c:choose>


										</c:when>
									</c:choose>
								</c:when>
								<c:when test="${ promoWanted==false  }">

									<input id="cardbrns" type="submit"
										class="btn btn-lg btn-block btn-outline-primary"
										value="Obtenir des promotions">

									

								</c:when>
								



							</c:choose>
						</div>
					</form:form>
				</div>

				<!-- fin de carte -->
			</c:forEach>

		</div>

		<footer>
        <div class="row">
            <div class="col-md-6">
                <p><img style="width:50px;" alt="" src="pics/Tasty.png"> droits d'auteur © 2021 M.B</p>
            </div>
            <div class="col-md-6 text-md-right">
                <a href="#" class="text-dark">
              Conditions d'utilisation</a> 
                <span class="text-muted mx-2">|</span> 
                <a href="#" class="text-dark">Politique de confidentialité</a>
            </div>
        </div>
    </footer>
	</div>
	
	
	
	
	
	
	
	
</div>
     
	<!-- Bootstrap core JavaScript
    ================================================== -->
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