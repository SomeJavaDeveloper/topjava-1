package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(7);

    static {
        Meal meal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        meal1.setUserId(1);
        meal1.setId(1);
        repository.put(1, meal1);
        Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        meal2.setUserId(2);
        meal2.setId(2);
        repository.put(2, meal2);
        Meal meal3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        meal3.setUserId(1);
        meal3.setId(3);
        repository.put(3, meal3);

    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
    }

    @Override
    public boolean delete(int userId, int mealId) {
        if(repository.get(mealId) != null){
            return repository.remove(mealId) != null;
        } else return false;
    }

    @Override
    public Meal get(int userId, int mealId) {
        return repository.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().
                filter(x -> x.getUserId().equals(userId)).
                sorted(Comparator.comparing(Meal::getDateTime)).
                collect(Collectors.toList());
    }
}

