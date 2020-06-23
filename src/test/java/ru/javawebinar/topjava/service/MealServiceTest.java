package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() throws Exception{
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, USER_MEAL);
    }

    @Test
    public void getNotFount() throws Exception{
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getWrongUSer() throws Exception{
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() throws Exception{
        service.delete(USER_MEAL_ID, USER_ID);
        assertNull(repository.get(USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception{
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteWrongUser() throws Exception{
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> adminMeals = service.getBetweenInclusive(
                LocalDate.of(2020,10,19),
                LocalDate.of(2021,1,12),
                ADMIN_MEAL_ID);
        assertMatch(adminMeals.get(0), ADMIN_MEAL);
        assertEquals(adminMeals.size(), 1);

        List<Meal> userMeals = service.getBetweenInclusive(
                LocalDate.of(2019,10,19),
                LocalDate.of(2021,1,12),
                USER_MEAL_ID);
        assertMatch(userMeals.get(0), USER_MEAL);
        assertEquals(userMeals.size(), 1);
    }

    @Test
    public void getAll() {
        List<Meal> adminMeals = service.getAll(ADMIN_MEAL_ID);
        assertMatch(adminMeals.get(0), ADMIN_MEAL);
        assertEquals(adminMeals.size(), 1);

        List<Meal> userMeals = service.getAll(USER_MEAL_ID);
        assertMatch(userMeals.get(0), USER_MEAL);
        assertEquals(userMeals.size(), 1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertEquals(updated, USER_MEAL);
    }

    @Test
    public void updateWrongUser() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}