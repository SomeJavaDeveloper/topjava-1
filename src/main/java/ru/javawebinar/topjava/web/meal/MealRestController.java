package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Controller
public class MealRestController extends AbstractMealController{

    @Override
    public List<Meal> getAll(int userId) {
        return super.getAll(userId);
    }

    @Override
    public Meal get(int userId, int mealId) {
        return super.get(userId, mealId);
    }

    @Override
    public Meal create(int userId, Meal meal) {
        return super.create(userId, meal);
    }

    @Override
    public void delete(int userId, int mealId) {
        super.delete(userId, mealId);
    }

    @Override
    public void update(int userId, Meal meal, int mealId) {
        super.update(userId, meal, mealId);
    }

}