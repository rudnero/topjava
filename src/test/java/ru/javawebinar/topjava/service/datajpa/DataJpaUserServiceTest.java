package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWhithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER.setMeals(user.getMeals());
        assertThat(user).isEqualToIgnoringGivenFields(USER,"registered", "roles");
    }

    @Test
    public void getWhithoutMeals() {
        User user = service.getWithMeals(USER_WITHOUT_ID);
        USER_WITHOUT.setMeals(user.getMeals());
        assertThat(user).isEqualToIgnoringGivenFields(USER_WITHOUT,"registered", "roles");
    }
}