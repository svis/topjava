package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.InMemoryData;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.InMemoryData.mapMeal;
import static ru.javawebinar.topjava.util.TimeUtil.stringToLocalDateTime;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("save meal data in POST");
        request.setCharacterEncoding("UTF-8");
        String sid = request.getParameter("id");
        Long id = null;
        if (!sid.isEmpty()) id = Long.valueOf(sid);

        Meal meal = new Meal(id,
                stringToLocalDateTime(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        mapMeal.saveMeal(id, meal);
        response.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action == null ? "getAll" : action) {

            case "getAll":
                log.info("getAll");
                request.setAttribute("meals", MealsUtil.getWithExceeded(mapMeal.listMeals()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;

            case "update":
                log.info("update GET");
                request.setAttribute("meals", mapMeal.getMealById(Long.valueOf(request.getParameter("id"))));
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;

            case "create":
                log.info("create GET");
                Meal meal = new Meal();
                request.setAttribute("meals", meal);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;

            case "delete":
                log.info("delete GET");
                mapMeal.deleteMeal(Long.valueOf(request.getParameter("id")));
                response.sendRedirect("meals");
                break;
        }
    }

}