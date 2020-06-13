package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

interface MealController {

    List<Meal> getAll(int userId);

    Meal get(int id);

    Meal create(Meal meal);

    void delete(int id);

    void update(Meal meal, int id);
}
