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
            <th width="500">Date</th>
            <th width="500">Description</th>
            <th width="500">Calories</th>
        </tr>

            <%-- Data--%>
        <c:forEach items="${meals}" var="meal">
            <%--Set color--%>
            <c:choose>
                <c:when test="${meal.excess}">
                    <c:set var="color" value="green"/>
                </c:when>
                <c:otherwise>
                    <c:set var="color" value="red"/>
                </c:otherwise>
            </c:choose>
            <tr>
                <td colspan="3">
                    <hr>
                </td>
            </tr>
            <tr style="color: ${color}">
                <td><%--deleting 'T' from date--%>
                    <c:forTokens items="${meal.getDateTime()}" delims="T" var="time">
                        ${time}
                    </c:forTokens>
                </td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
