<%@ page import="java.util.Map" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Test</title>
</head>
<body>
<ol>
<c:forEach items="${menu}" var="menuItem">
    <li><a href=/garage-web${menuItem.value}>${menuItem.key}</a></li>
</c:forEach>
</ol>
</body>
</html>