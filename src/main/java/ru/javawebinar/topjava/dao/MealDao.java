package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    Meal save(Meal meal);

    void delete(Long id);

    Meal getById(Long id);

    List<Meal> getAll();

}
