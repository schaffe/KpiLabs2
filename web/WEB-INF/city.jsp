<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jquery-latest.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
    <title>Cities</title>
</head>
<body>

<div></div>

<form action="${pageContext.request.contextPath}/city/" method="post">
    <table>
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add"></td>
        </tr>
    </table>
</form>

<h3>Cities</h3>
<table class="table" border="0" id="table">
    <tr>
        <th onclick="sortField('name')">City</th>
        <th onclick="sortField('population')">Population</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    <tbody id="table_body">
    </tbody>
</table>
</body>
</html>