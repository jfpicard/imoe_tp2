<%@include file="includes/header.jsp" %>

<center>
<form method="post" action="<%=application.getContextPath()%>/edit">
	
	<!-- EVENEMENT ID - HIDDEN -->
	<input type="hidden" name="eventId" value="${event.id}">
		
	<table>
		<!-- TITRE -->
		<tr>
			<td><b>Titre :</b></td>
			<td><input type="text" name="title" value="<c:if test="${!empty title}">${title}</c:if><c:if test="${empty title}">${event.titre}</c:if>"></td>
		</tr>
		<c:if test="${!empty titleError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${titleError}</td>
			</tr>
		</c:if>
		
		<!-- TYPE -->
		<tr>
			<td><b>Type :</b></td>
			<td> <select name="type">					
		 			<c:forEach var="t" items="${types}">
		    			<option value="${t.id}" <c:if test="${(!empty type) && (type == t.id)}">selected</c:if><c:if test="${(empty type) && (event.type.id == t.id)}">selected</c:if>>${t.libelle}</option>
		    		</c:forEach>
		 		</select>
		 	</td>
		</tr>
		<c:if test="${!empty typeError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${typeError}</td>
			</tr>
		</c:if>
		
		<!-- DATE DEBUT -->
		<tr>
			<td><b>Date début :</b></td>
			<td><input type="datetime" name="begin" value="<c:if test="${!empty begin}">${begin}</c:if><c:if test="${empty begin}">${event.dateDebut}</c:if>"></td>
		</tr>
		<c:if test="${!empty beginError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${beginError}</td>
			</tr>
		</c:if>
		
		<!-- DATE FIN -->
		<tr>
			<td><b>Date fin :</b></td>
			<td><input type="datetime" name="end" value="<c:if test="${!empty end}">${end}</c:if><c:if test="${empty end}">${event.dateFin}</c:if>"></td>
		</tr>
		<c:if test="${!empty endError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${endError}</td>
			</tr>
		</c:if>
		
		<!-- DESCRIPTION -->
		<tr>
			<td><b>Description :</b></td>
			<td><input type="text" name="description" value="<c:if test="${!empty description}">${description}</c:if><c:if test="${empty end}">${event.description}</c:if>"></td>
		</tr>
	</table>
	 
	 <input type="submit" value="Mettre à jour">
	 
</form>
<hr/>

<a href="<%=application.getContextPath()%>/listing">Retour à la page principale</a>

</center>

<%@include file="includes/footer.jsp" %>
