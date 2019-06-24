package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MealTestData.USER_MEAL_ID_3, UserTestData.USER_ID);
        assertEquals(meal, MealTestData.USER_MEAL_3);
    }

    @Test(expected = NotFoundException.class)
    public void getStrangerMeal() {
        service.get(MealTestData.USER_MEAL_ID_3, UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MealTestData.USER_MEAL_ID_3, UserTestData.USER_ID);
        List<Meal> expectedlMeals = Arrays.asList(
                MealTestData.USER_MEAL_4,
                MealTestData.USER_MEAL_5,
                MealTestData.USER_MEAL_6,
                MealTestData.USER_MEAL_7,
                MealTestData.USER_MEAL_8
        );
        assertEquals(expectedlMeals, service.getAll(UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteStrangeMeal() {
        service.delete(MealTestData.USER_MEAL_ID_3, UserTestData.ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actualMeals = service.getBetweenDates(LocalDate.of(2015, 05, 30), LocalDate.of(2015, 05, 30), UserTestData.USER_ID);
        List<Meal> expectedMeals = Arrays.asList(
                MealTestData.USER_MEAL_5,
                MealTestData.USER_MEAL_4,
                MealTestData.USER_MEAL_3
        );
        assertEquals(expectedMeals, actualMeals);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> actualMeals = service.getBetweenDateTimes(LocalDateTime.of(2015, 05, 30, 10, 0), LocalDateTime.of(2015, 05, 30, 20, 0), UserTestData.USER_ID);
        List<Meal> expectedMeals = Arrays.asList(
                MealTestData.USER_MEAL_5,
                MealTestData.USER_MEAL_4,
                MealTestData.USER_MEAL_3
        );
        assertEquals(expectedMeals, actualMeals);
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(UserTestData.ADMIN_ID);
        List<Meal> expectedMeals = Arrays.asList(
                MealTestData.ADMIN_MEAL_9,
                MealTestData.ADMIN_MEAL_10,
                MealTestData.ADMIN_MEAL_11,
                MealTestData.ADMIN_MEAL_12,
                MealTestData.ADMIN_MEAL_13,
                MealTestData.ADMIN_MEAL_14
        );
        assertEquals(expectedMeals, actualMeals);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MealTestData.USER_MEAL_3);
        updated.setCalories(700);
        updated.setDescription("updated");
        service.update(updated, UserTestData.USER_ID);
        assertEquals(updated, service.get(MealTestData.USER_MEAL_ID_3, UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateStrangeMeal() {
        Meal updated = new Meal(MealTestData.USER_MEAL_3);
        updated.setCalories(700);
        updated.setDescription("updated");
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, 01, 01, 0,0), "Новый ужин", 100);
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        newMeal.setId(created.getId());

        List<Meal> expectedlMeals = Arrays.asList(
                MealTestData.USER_MEAL_3,
                MealTestData.USER_MEAL_4,
                MealTestData.USER_MEAL_5,
                MealTestData.USER_MEAL_6,
                MealTestData.USER_MEAL_7,
                MealTestData.USER_MEAL_8,
                newMeal
        );
        assertEquals(expectedlMeals, service.getAll(UserTestData.USER_ID));
    }
}