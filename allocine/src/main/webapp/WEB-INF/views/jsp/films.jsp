<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des films</title>
<s:url value="/resources/css/bonjour.css" var="coreStyle" />
<s:url value="/resources/css/bootstrap.css" var="bootstrapStyle" />
<link href="${coreStyle}" rel="stylesheet" />
<link href="${bootstrapStyle}" rel="stylesheet" />
</head>
<body>
<h2> Liste des films</h2>
<div class="contener"> 
<div class="well">
	<form method="post" action="addFilm">
		<div class="form-group">
	 		<label for="titre"> Titre du film</label>
	 		<input type ="text" class="form-control" id="titre" name="titre"/>
	 	 </div>
	 	<div class="form-group">
	 		<label for="realisateur"> realisateur </label>
	 		<input type ="text" class="form-control" id="realisateur" name="realisateur"/>
	 	 </div> 
	 	 <div class="form-group">
	 		<label for="annee"> Annee</label>
	 		<input type ="number" class="form-control" id="annee" name="annee"/>
	 	 </div>
	 	 <div class="form-group">
	 		<label for="synopsis"> "Synopsis" du film</label>
	 		<input type ="text" class="form-control" id="synopsis" name="synopsis"/>
	 	 </div>
	 	 <input type="submit" class="btn btn-primary" value="ajouter"/>
	</form>
</div>
	<table border="1" class="table table-striped">
		<thead>
			<tr> <th>titre</th> <th>realisateur</th> <th>annee</th> <th>synopsis</th> </tr>
		</thead>
		<tbody>
			<c:forEach var="film" items="${films}">
				<tr>
					<td> ${film.titre} </td>
					<td> ${film.realisateur} </td>
					<td> ${film.annee} </td>
					<td> ${film.synopsis} </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>