<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<c:if test="${!empty meals}">
    <table class="tg">
            <%-- Head--%>
        <tr align="left">
            <th width="300">Date</th>
            <th width="500">Description</th>
            <th width="100">Calories</th>
            <th width="100">Edit</th>
            <th width="100">Delete</th>
        </tr>

            <%-- Data--%>
        <c:forEach items="${meals}" var="meals">
            <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr>
                <td colspan="5">
                    <hr>
                </td>
            </tr>
            <tr style="color:${meals.excess ? 'red':'green'}">
                <p><input type="hidden" name="id" value="<c:out value="${meals.id}" />" /></p>
                <td><%--deleting 'T' from date--%>
                    <c:forTokens items="${meals.getDateTime()}" delims="T" var="time">
                        ${time}
                    </c:forTokens>
                </td>
                <td>${meals.getDescription()}</td>
                <td>${meals.getCalories()}</td>
                <td><a href="meals?action=update&id=${meals.getId()}">Изменить</a></td>
                <td><a href="meals?action=delete&id=${meals.getId()}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<h3><a href="index.html">На главную</a></h3>
</body>
</html>
