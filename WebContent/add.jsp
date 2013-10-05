<%@include file="includes/header.jsp" %>

<center>
<form method="post" action="<%=application.getContextPath()%>/mvc2/add">
	
	<table>
		<!-- TITRE -->
		<tr>
			<td><b>Titre :</b></td>
			<td><input type="text" name="title" value="<c:if test="${!empty title}">${title}</c:if>"></td>
		</tr>
		<c:if test="${!empty titleError}">
			<tr>
				<td colspan="2" class="error">${titleError}</td>
			</tr>
		</c:if>
		
		<!-- TYPE -->
		<tr>
			<td><b>Type :</b></td>
			<td> <select name="type">					
		 			<c:forEach var="t" items="${types}">
		    			<option value="${t.id}" <c:if test="${(!empty type) && (type == t.id)}">selected</c:if>>${t.libelle}</option>
		    		</c:forEach>
		 		</select>
		 	</td>
		</tr>
		<c:if test="${!empty typeError}">
			<tr>
				<td colspan="2" class="error">${typeError}</td>
			</tr>
		</c:if>
		
		<!-- DATE DEBUT -->
		<tr>
			<td><b>Date début :</b></td>
			<td><input type="datetime" name="begin" value="<c:if test="${!empty begin}">${begin}</c:if>"></td>
		</tr>
		<c:if test="${!empty beginError}">
			<tr>
				<td colspan="2" class="error">${beginError}</td>
			</tr>
		</c:if>
		
		<!-- DATE FIN -->
		<tr>
			<td><b>Date fin :</b></td>
			<td><input type="datetime" name="end" value="<c:if test="${!empty end}">${end}</c:if>"></td>
		</tr>
		<c:if test="${!empty endError}">
			<tr>
				<td colspan="2" class="error">${endError}</td>
			</tr>
		</c:if>
		
		<!-- DESCRIPTION -->
		<tr>
			<td><b>Description :</b></td>
			<td><input type="text" name="description" value="<c:if test="${!empty description}">${description}</c:if>"></td>
		</tr>
	</table>
	 
	 <input type="submit" value="Ajouter">
	 
</form>
<hr/>

<a href="<%=application.getContextPath()%>/mvc2">Retour à la page principale</a>

</center>

<%@include file="includes/footer.jsp" %>
