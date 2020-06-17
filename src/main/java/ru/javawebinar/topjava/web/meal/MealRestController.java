package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        int userId = authUserId();
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        int userId = authUserId();
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getFilter(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = authUserId();
        log.info("get filter for  dates({} - {}) times({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getFilterDate(
                startDate != null ? startDate : LocalDate.MIN,
                endDate != null ? endDate : LocalDate.MAX, userId);

        return MealsUtil.getFilteredTos(mealsDateFiltered, authUserCaloriesPerDay(),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX
        );
    }

}