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

<div class="bg"></div>
<form action="${pageContext.request.contextPath}/city/" id="add_city_form">
    <p><label for="add_city_form_name">
        Name
        <input type="text" name="name" id="add_city_form_name" required>
    </label></p>
    <p><label for="add_city_form_population">
        Population
        <input type="number" min="0" name="population" id="add_city_form_population">
    </label></p>
    <p><input type="submit" value="Add"></p>
</form>
<h3>Cities</h3>
<table class="table" border="0" id="table">
    <tr>
        <th onclick="sortByColumn('name')">City</th>
        <th onclick="sortByColumn('population')">Population</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
    <tbody id="table_body">
    </tbody>
</table>
</body>
</html>