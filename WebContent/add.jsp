<%@include file="includes/header.jsp" %>

<center>
<form method="post" action="<%=application.getContextPath()%>/add">
	
	<table>
		<tr>
			<td><b>Titre :</b></td>
			<td><input type="text" name="title" value="<c:if test="${!empty title}">${title}</c:if>"></td>
		</tr>
		<c:if test="${!empty titleError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${titleError}</td>
			</tr>
		</c:if>
		<tr>
			<td><b>Type :</b></td>
			<td> <select name="type">
		 			<c:forEach var="type" items="${types}">
		    			<option value="${type.id}">${type.libelle}</option>
		    		</c:forEach>
		 		</select>
		 	</td>
		</tr>
		<c:if test="${!empty typeError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${typeError}</td>
			</tr>
		</c:if>
		<tr>
			<td><b>Date début :</b></td>
			<td><input type="datetime" name="begin" value="<c:if test="${!empty begin}">${begin}</c:if>"></td>
		</tr>
		<c:if test="${!empty beginError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${beginError}</td>
			</tr>
		</c:if>
		<tr>
			<td><b>Date fin :</b></td>
			<td><input type="datetime" name="end" value="<c:if test="${!empty end}">${end}</c:if>"></td>
		</tr>
		<c:if test="${!empty endError}">
			<tr>
				<td colspan="2" style="padding-left: 30px; color: red; font-weigth: bold;">${endError}</td>
			</tr>
		</c:if>
		<tr>
			<td><b>Description :</b></td>
			<td><input type="text" name="description"></td>
		</tr>
	</table>
	 
	 <input type="submit" value="Ajouter">
	 
</form>
<hr/>

<a href="<%=application.getContextPath()%>/listing">Retour à la page principale</a>

</center>

<%@include file="includes/footer.jsp" %>
