package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAll(int userId){
        return repository.getAll(userId);
    }

    public Meal get(int userId, int mealId){
        checkUserId(userId, repository.get(userId, mealId));
        return checkNotFoundWithId(repository.get(userId, mealId), mealId);
    }

    public Meal create(int userId, Meal meal){
        return repository.save(userId, meal);
    }

    public void delete(int userId, int mealId){
        checkNotFoundWithId(repository.delete(userId, mealId), mealId);
    }

    public void update(int userId, Meal meal){
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }
}