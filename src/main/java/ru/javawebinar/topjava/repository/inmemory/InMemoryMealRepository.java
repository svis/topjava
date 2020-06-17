package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    // userId --> mealId --> meal
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),1);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 30, 10, 0), "Завтрак", 500),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 30, 13, 0), "Обед", 1000),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 30, 20, 0), "Ужин", 500),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 31, 13, 0), "Обед", 500),2);
        save(new Meal(LocalDateTime.of(2019, Month.JANUARY, 31, 20, 0), "Ужин", 410),2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, HashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save {}", meal);
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getFilteredDate(LocalDate startDate, LocalDate endDate, int userId) {
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }


    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

}

