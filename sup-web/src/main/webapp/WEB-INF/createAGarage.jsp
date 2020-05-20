<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 15/05/2020
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Creation of a Garage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/MyGarage.css"/>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/doCreateGarage">
    <label for="name">Nom : </label>
    <input type="text" name="name" id="name"/><br/>
    <c:forEach items="${lstDescLocation}" var="locationDesc">
        <input type="radio" name="location" value="${locationDesc.locationName}" id="${locationDesc.locationName}"/>
        <label for="${locationDesc.locationName}">${locationDesc.locationName}</label>
        <br/>
    </c:forEach>
    <input type="submit"/>
</form>
</body>
</html>
