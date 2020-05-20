<%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 11/05/2020
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Description des garages</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/MyGarage.css" />
</head>
<body>
<table class="garages">
    <caption>Liste des garages</caption>
    <thead>
    <tr>
        <th scope=col>Nom
        </th>
        <th scope=col>Location
        </th>
        <th scope=col>Nombre de véhicule(s)
        </th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${lstDescGarage}" var="garageDesc">
        <tr>
            <td>${garageDesc.name}</td>
            <td>${garageDesc.locationName}</td>
            <td>${garageDesc.numberOfCars}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
