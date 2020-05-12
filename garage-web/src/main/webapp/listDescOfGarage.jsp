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
<table style="width: 740px; height: 238px;" border="1">
    <thead>
    <tr>
        <th>Nom<br>
        </th>
        <th>Location<br>
        </th>
        <th>Nombre de véhicule(s)<br>
        </th>
    </tr>
    </thead>

    <tbody>
    <%
        List<GarageDesc> lstDescGarage = (List<GarageDesc>) request.getAttribute("lstDescGarage");
        for (GarageDesc garageDesc : lstDescGarage) {
    %>
    <tr>
        <td>
            <%
                out.println(garageDesc.getName());
            %>
            <br>
        </td>
        <td>
            <%
                out.println(garageDesc.getLocation());
            %>
            <br>
        </td>
        <td>
            <%
                out.println(garageDesc.getNumberOfCars());
            %>
            <br>
        </td>
    </tr>

    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
