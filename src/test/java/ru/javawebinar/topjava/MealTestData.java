package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID_3 = START_SEQ+2;
    public static final int USER_MEAL_ID_4 = START_SEQ+3;
    public static final int USER_MEAL_ID_5 = START_SEQ+4;
    public static final int USER_MEAL_ID_6 = START_SEQ+5;
    public static final int USER_MEAL_ID_7 = START_SEQ+6;
    public static final int USER_MEAL_ID_8 = START_SEQ+7;

    public static final int ADMIN_MEAL_ID_9 = START_SEQ+8;
    public static final int ADMIN_MEAL_ID_10 = START_SEQ+9;
    public static final int ADMIN_MEAL_ID_11 = START_SEQ+10;
    public static final int ADMIN_MEAL_ID_12 = START_SEQ+11;
    public static final int ADMIN_MEAL_ID_13 = START_SEQ+12;
    public static final int ADMIN_MEAL_ID_14 = START_SEQ+13;

    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3, LocalDateTime.of( 2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_4 = new Meal(USER_MEAL_ID_4, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_5 = new Meal(USER_MEAL_ID_5, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL_6 = new Meal(USER_MEAL_ID_6, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_7 = new Meal(USER_MEAL_ID_7, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL_8 = new Meal(USER_MEAL_ID_8, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal ADMIN_MEAL_9 = new Meal(ADMIN_MEAL_ID_9, LocalDateTime.of( 2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_10 = new Meal(ADMIN_MEAL_ID_10, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL_11 = new Meal(ADMIN_MEAL_ID_11, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal ADMIN_MEAL_12 = new Meal(ADMIN_MEAL_ID_12, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_13 = new Meal(ADMIN_MEAL_ID_13, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal ADMIN_MEAL_14 = new Meal(ADMIN_MEAL_ID_14, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
