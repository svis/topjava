package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.util.TimeUtil.stringToLocalDateTime;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealDaoImpl mapMeal;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mapMeal = new MealDaoImpl();
        {
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 1000));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед", 300));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин", 400));
            mapMeal.save(new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение", 1400));
        }
    }

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

        mapMeal.save(meal);
        response.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action == null ? "getAll" : action) {

            case "getAll":
                log.info("getAll");
                request.setAttribute("meals", MealsUtil.getWithExceeded(mapMeal.getAll()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;

            case "update":
                log.info("update GET");
                request.setAttribute("meals", mapMeal.getById(Long.valueOf(request.getParameter("id"))));
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
                mapMeal.delete(Long.valueOf(request.getParameter("id")));
                response.sendRedirect("meals");
                break;

            default:
                log.info("action unknown");
                request.setAttribute("meals", MealsUtil.getWithExceeded(mapMeal.getAll()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;

        }
    }
}