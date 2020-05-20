<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 19/05/2020
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Unregistre of Garages</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/MyGarage.css"/>

</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/doDeleteGarage">
    <c:forEach items="${lstDescGarage}" var="garageDesc">
        <input type="checkbox" name="garages" value="${garageDesc.name}" id="${garageDesc.name}"/>
        <label for="${garageDesc.name}">${garageDesc.name} at ${garageDesc.locationName}</label>
        <br/>
    </c:forEach>
    <input type="submit" value="Unregistre selected garage(s)"/>
</form>

</body>
</html>
