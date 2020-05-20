<%@ page import="java.util.Map" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8"/>
    <title>Fonctionnalité de MyGarage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/MyGarage.css" />
</head>
<body>
<ol><p class="menuP">Menu Principal</p>
<c:forEach items="${menu}" var="menuItem">
    <li class="menuItem"><a href=${pageContext.request.contextPath}${menuItem.value} target="Contenu">${menuItem.key}</a></li>
</c:forEach>
</ol>
</body>
</html>
