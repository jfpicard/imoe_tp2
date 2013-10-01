<%@include file="includes/header.jsp" %>

<center>
<table border="1">
	<tr>
		<td><b>Titre</b></td>
		<td><b>Date début</b></td>
		<td><b>Date fin</b></td>
		<td><b>[COMMANDES]</b></td>
	</tr>

      <c:forEach var="evt" items="${evts}">
		<tr>
			<td>${evt.titre}</td>
			<td>fmt:formatDate sur ${evt.dateDebut}</td>
			<td>fmt:formatDate sur ${evt.dateFin}</td>
			<td>
				<a href="<%=application.getContextPath()%>/listing/detail?id=${evt.id}">Détail</a><br/>
				<a href="<%=application.getContextPath()%>/listing/edit?id=${evt.id}">Editer</a><br/>
				<a href="<%=application.getContextPath()%>/listing/delete?id=${evt.id}">Supprimer</a>
			</td>
		</tr>
      </c:forEach>

</table>
<hr/>

<a href="<%=application.getContextPath()%>/add">Ajouter un nouvel événement</a>

</center>

<%@include file="includes/footer.jsp" %>
