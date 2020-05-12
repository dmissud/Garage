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
    <%
        Map<String, String> menu = (Map<String, String>) request.getAttribute("menu");
        for (Map.Entry<String, String> menuItem : menu.entrySet()) {
    %>
    <li><a href=/garage-web<%
        out.println(menuItem.getValue());
    %>><%
        out.println(menuItem.getKey());
    %></a></li>
    <%
        }
    %>
</ol>
</body>
</html>