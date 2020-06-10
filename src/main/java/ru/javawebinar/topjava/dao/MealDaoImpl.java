package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MealDaoImpl implements MealDao {

    private final static Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

    private final static AtomicReference<Long> currentTime = new AtomicReference<>(System.currentTimeMillis());

    @Override
    public Meal save(Meal meal) {
        // create meal
        if (meal.getId() == null) {
            meal.setId(nextId());
            if (mealMap.putIfAbsent(meal.getId(), meal) == null) return meal;
            return null;
        }
        // update meal
        else {
            if (mealMap.replace(meal.getId(), meal) == null) return null;
            return meal;
        }
    }

    @Override
    public void delete(Long id) {
        mealMap.remove(id);
    }

    @Override
    public Meal getById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    private static Long nextId() {
        return currentTime.accumulateAndGet(System.currentTimeMillis(),
                (prev, next) -> next > prev ? next : prev + 1);
    }

}
