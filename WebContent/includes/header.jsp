<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
	<title>Gestionnaire d'événements.</title>
	<link rel=stylesheet type="text/css" href="<%=application.getContextPath()%>/css/style.css">
</head>
<body background="<%=application.getContextPath()%>/img/background.jpg" bottommargin="0" leftmargin="0" marginheight="0" marginwidth="0" rightmargin="0" topmargin="0">
<center>
<table width="765" height="100%" cellpadding="0" cellspacing="0" border="0" background="<%=application.getContextPath()%>/img/mainbackground.jpg">
	<tr valign="top">
		<td>
			<!-- En-tête -->
			<table width="764" height="97" cellpadding="0" cellspacing="0" border="0">
				<tr valign="top">
					<td width="248">
						<img src="<%=application.getContextPath()%>/img/logo.jpg" width="248" height="97" border="0" alt="">
					</td>
					<td width="100%" background="<%=application.getContextPath()%>/img/toplogobg.jpg">
						<img src="<%=application.getContextPath()%>/img/toplogobg.jpg" width="22" height="97" border="0" alt="">
					</td>
				</tr>
			</table>


			<!-- Lien entre l'en-tête et le corps -->
			<table width="764" height="42" cellpadding="0" cellspacing="0" border="0">
				<tr valign="top">
					<td width="169"><img src="<%=application.getContextPath()%>/img/left1.jpg" width="169" height="42" border="0" alt=""></td>
					<td width="100%" background="<%=application.getContextPath()%>/img/left1bg.jpg">
						<img src="<%=application.getContextPath()%>/img/left1bg.jpg" width="20" height="42" border="0" alt="">
					</td>
				</tr>
			</table>


			<!-- Corps -->
			<table width="764" cellpadding="0" cellspacing="0" border="0">
				<tr valign="top">
					<!-- Menu -->
					<td width="150">
						<img src="<%=application.getContextPath()%>/img/menudivider.jpg" width="150" height="6" border="0" alt=""><BR>
						&nbsp; <a href="<%=application.getContextPath()%>/index.jsp">Home</a><BR>
						<img src="<%=application.getContextPath()%>/img/menudivider.jpg" width="150" height="6" border="0" alt=""><BR>
					</td>
					<!-- Colonne vide d'ajustement -->
					<td width="10">&nbsp;</td>
					<td width="744">