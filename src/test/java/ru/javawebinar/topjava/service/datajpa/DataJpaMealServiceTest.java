package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWhitUser() {
        Meal actual = service.getWhitUser(ADMIN_MEAL_ID, ADMIN_ID);
        ADMIN_MEAL1.setUser(actual.getUser());
        assertThat(actual).isEqualToComparingFieldByField(ADMIN_MEAL1);
    }
}