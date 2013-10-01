<%@include file="includes/header.jsp" %>

<center>	
	<table>
		<tr>
			<td><b>Titre :</b></td>
			<td><c:out value='${event.titre}' /></td>
		</tr>
		<tr>
			<td><b>Type :</b></td>
			<td><c:out value='${event.type.libelle}' /></td>
		</tr>
		<tr>
			<td><b>Date début :</b></td>
			<td><c:out value='${event.dateDebut}' /></td>
		</tr>
		<tr>
			<td><b>Date fin </b></td>
			<td><c:out value='${event.dateFin}' /></td>
		</tr>
		<tr>
			<td><b>Description :</b></td>
			<td><c:out value='${event.description}' /></td>
		</tr>
	</table>
<hr/>

<a href="<%=application.getContextPath()%>/listing">Retour à la page principale</a>
</center>

<%@include file="includes/footer.jsp" %>
