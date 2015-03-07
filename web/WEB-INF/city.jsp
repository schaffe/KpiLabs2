<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            alert('LOL2');
        });
    </script>
    <title>Cities</title>
</head>
<body>

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
<c:if test="${!empty cityList}">
    <table class="data" border="0" id="table">
        <tr>
            <th>City</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${cityList}" var="city">
            <tr>
                <td>${city.name}</td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>