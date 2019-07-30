package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(post(REST_URL + "filter")
                .param("startDate", "2015-05-30")
                .param("startTime", "01:00")
                .param("endDate", "2015-05-31")
                .param("endTime", "23:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void getBetweenByDate() throws Exception {
        mockMvc.perform(post(REST_URL + "filter")
                .param("startDate", "2015-05-30")
                .param("endDate", "2015-05-31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void getBetweenByTime() throws Exception {
        mockMvc.perform(post(REST_URL + "filter")
                .param("startTime", "10:00")
                .param("endTime", "13:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getFilteredWithExcess(MEALS, SecurityUtil.authUserCaloriesPerDay(), DateTimeUtil.parseLocalTime("10:00"), DateTimeUtil.parseLocalTime("13:00"))));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(SecurityUtil.authUserId()), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testCreate() throws Exception {
        Meal expected = MealTestData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        expected.setUser(returned.getUser());

        assertMatch(returned, expected);
        assertMatch(service.getAll(SecurityUtil.authUserId()), returned, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = MealTestData.getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(updated, service.get(MEAL1_ID, SecurityUtil.authUserId()));
    }
}
