package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class MealsUtil {

    // Default values
    public static final int CALORIES_PER_DAY = 2000;
    public static final LocalTime TIME_START = LocalTime.MIN;
    public static final LocalTime TIME_END = LocalTime.MAX;

    public static List<MealTo> getWithExceeded(List<Meal> meals) {
        return getFilteredWithExceeded(meals, TIME_START, TIME_END, CALORIES_PER_DAY);
    }

    public static List<MealTo> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))

                );

        return meals.stream()
                .filter(meal -> isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
