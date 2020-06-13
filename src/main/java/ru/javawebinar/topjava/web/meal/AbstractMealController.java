package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

public class AbstractMealController implements MealController {

    private static final Logger log = getLogger(AbstractMealController.class);

    @Autowired
    private MealService mealService;

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return mealService.getAll(userId);
    }

    @Override
    public Meal get(int id) {
        log.info("get {}", id);
        return mealService.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return mealService.create(meal);
    }

    @Override
    public void delete(int id) {
        log.info("delete {}", id);
        mealService.delete(id);
    }

    @Override
    public void update(Meal meal, int id) {
        log.info("update {} with id = {}", meal, id);
        assureIdConsistent(meal, id);
        mealService.update(meal, id);
    }
}
