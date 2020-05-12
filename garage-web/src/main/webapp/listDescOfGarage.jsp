<%@ page import="org.dbs.garage.application.port.out.GarageDesc" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: danie
  Date: 11/05/2020
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Description des garages</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Nom
        </th>
        <th>Location
        </th>
        <th>Nombre de véhicule(s)
        </th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${lstDescGarage}" var="garageDesc">
        <tr>
            <td>${garageDesc.name}</td>
            <td>${garageDesc.location}</td>
            <td>${garageDesc.numberOfCars}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
