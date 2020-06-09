package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    public void saveMeal(Long id, Meal meal);

    public void deleteMeal(Long id);

    public Meal getMealById(Long id);

    public List<Meal> listMeals();

}
