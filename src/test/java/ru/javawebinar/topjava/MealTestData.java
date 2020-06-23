package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_MEAL_ID = START_SEQ;
    public static final int ADMIN_MEAL_ID = START_SEQ + 1;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2020,11,19, 21, 47), "123", 123);
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020,12,19, 20,47), "456", 456);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL);
        updated.setDateTime(LocalDateTime.of(2020,12,12, 11, 23));
        updated.setDescription("new desc");
        updated.setCalories(242);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "new Meal", 1000);
    }
//
//    public static void assertMatch(User actual, User expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
//    }
//
//    public static void assertMatch(Iterable<User> actual, User... expected) {
//        assertMatch(actual, Arrays.asList(expected));
//    }
//
//    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
//        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
//    }
}
