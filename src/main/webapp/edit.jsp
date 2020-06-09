<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<html>
<head>
    <title>Редактор еды</title>
</head>
<body>
<div><h2>Изменить параметры</h2></div>
<div>
    <jsp:useBean id="meals" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <form method="post" action="meals">
        <br>
        <input hidden type="number" name="id" value="${meals.id}"/>
        <br>
        <div>
            yyyy-mm-dd hh:mm
        </div>
        <input type="text" value="${TimeUtil.toString(meals.dateTime)}" name="dateTime" required></dd>
        <br>
        <div>
            Description
        </div>
        <input type="text" name="description" value="${meals.description}"/>
        <br>
        <div>
            Calories
        </div>
        <input type="number" name="calories" value="${meals.calories}"/>
        <br>
        <br>
        <div>
            <button type="submit" class="btn btn-success" name="save" value="1">Сохранить</button>
            <button type="button" onclick="window.history.back()" class="btn btn-info" name="CancelEdit" value="1">
                Отмена
            </button>
        </div>
    </form>
</div>
</body>
</html>