<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<form method="POST" action='meals' name="editmeal">
    ID: <input type="text" readonly="readonly" name="id" value="${meal.id}"> <br />
    DateTime: <input type="text" name="dt" value="<c:out value="${meal.dateTime}" />" /> <br />
    Description: <input type="text" name="description" value="<c:out value="${meal.description}" />" /> <br />
    Calories: <input type="text" name="calories" value="<c:out value="${meal.calories}" />" /> <br />
    <button type="submit">save</button>
</form>
</body>
</html>
