package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static ArrayList<String> statistic = new ArrayList();

    @Autowired
    private MealService service;

    @Rule
    public ExpectedException throwm = ExpectedException.none();

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        private LocalDateTime start;
        private LocalDateTime end;

        @Override
        protected void starting(Description description) {
            start = LocalDateTime.now();
        }

        @Override
        protected void finished(Description description) {
            end = LocalDateTime.now();
            long ms = ChronoUnit.MILLIS.between(start, end);
            String result = "Test: '" + description.getMethodName() + "' was executing for " + ms + "ms";
            statistic.add(result);
            System.out.println(result);
        }
    };

    @AfterClass
    public static void after() {
        System.out.println(statistic);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    public void deleteNotFound() throws Exception {
        throwm.expect(NotFoundException.class);
        service.delete(1, USER_ID);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    public void deleteNotOwn() throws Exception {
        throwm.expect(NotFoundException.class);
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    public void getNotFound() throws Exception {
        throwm.expect(NotFoundException.class);
        service.get(1, USER_ID);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    public void getNotOwn() throws Exception {
        throwm.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    //@Test(expected = NotFoundException.class)
    @Test
    public void updateNotFound() throws Exception {
        throwm.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}