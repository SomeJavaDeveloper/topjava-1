package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public List<Meal> getAll(int userId){

        log.info("getAll");

        if (service == null) initService();

        System.out.println(service.getAll(userId));

        return service.getAll(userId);
    }

    public Meal get(int userId, int mealId){
        log.info("get {}", mealId);

        if (service == null) initService();

        return service.get(userId, mealId);
    }

    public Meal create(int userId, Meal meal){
        log.info("create {}", meal);

        if (service == null) initService();

        return service.create(userId, meal);
    }

    public void delete(int userId, int mealId){
        log.info("delete {}", mealId);

        if (service == null) initService();

        service.delete(userId, mealId);
    }

    public void update(int userId, Meal meal, int mealId){
        log.info("update {} with id={}", meal, mealId);

        if (service == null) initService();

        assureIdConsistent(meal, mealId);
        service.update(userId, meal);
    }

    private void initService(){
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            service = appCtx.getBean(MealService.class);
        }
    }
}
