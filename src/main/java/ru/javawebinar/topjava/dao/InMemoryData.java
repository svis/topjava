package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;


public class InMemoryData {

    public static MealDaoImpl mealMap = new MealDaoImpl();

    static {
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 1000));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед", 300));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин", 400));
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение", 1400));
    }

    public static void main(String[] args) {

        //    Test listMeals
        System.out.println("************* Test listMeals ***********");
        for (Meal meal : mealMap.listMeals()) {
            System.out.println(meal.toString());
        }

        //     Test saveMeal - add
        System.out.println("************* Test saveMeal - add ***********");
        mealMap.saveMeal(null, new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 20, 10, 0), "Добавление еды", 500));
        for (Meal meal : mealMap.listMeals()) {
            System.out.println(meal.toString());
        }
        //     Test saveMeal - update
        System.out.println("************* Test saveMeal - update ***********");
        for (Meal meal : mealMap.listMeals()) {
            mealMap.getMealById(meal.getId());
            meal = new Meal(meal.getId(), meal.getDateTime(), "Модификация еды", meal.getCalories());
            System.out.println(meal.toString());

        }

        //     Test deleteMeal
        System.out.println("************* Test deleteMeal & getMealByID ***********");
        for (Meal meal : mealMap.listMeals()) {
            mealMap.deleteMeal(meal.getId());
            meal = mealMap.getMealById(meal.getId());
            if ((meal == null)) {
                System.out.println("Удаление работает");
            } else {
                System.out.println(meal.toString());
            }
        }
    }
}