package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int userId);

    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Meal get(int id, int userId);

    List<Meal> getFilteredTime(LocalTime startTime, LocalTime endTime, int userId);

    List<Meal> getFilteredDate(LocalDate startDate, LocalDate endDate, int userId);

    List<Meal> getFilteredDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    List<Meal> getAll(int userId);

}
