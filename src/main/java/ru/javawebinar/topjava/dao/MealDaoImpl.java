package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.CurrentTimeId;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoImpl implements MealDao {

    private final static Map<Long, Meal> mealMap = new ConcurrentHashMap<>();


    @Override
    public void saveMeal(Long id, Meal meal) {
        if (id == null) {
            id = CurrentTimeId.nextId();
            meal.setId(id);
        }
        mealMap.put(id, meal);
    }

    @Override
    public void deleteMeal(Long id) {
        mealMap.remove(id);
    }

    @Override
    public Meal getMealById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> listMeals() {
        return new ArrayList<Meal>(mealMap.values());
    }
}
