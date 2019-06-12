<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr style="background-color:${meal.excess ? 'red' : 'greenyellow'}">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <td><fmt:formatDate pattern="yyyy-MMM-dd HH:mm" value="${parsedDateTime}" /></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td> <a href="meals?action=delete&id=<c:out value="${meal.id}"/>"> Delete </a> </td>
            <td> <a href="meals?action=edit&id=<c:out value="${meal.id}"/>"> Edit </a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="meals?action=add">Добавить</a>
</body>
</html>