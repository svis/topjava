package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService service;

    @Test
    public void testGet() throws Exception {
        perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () ->  service.get(MEAL1_ID, SecurityUtil.authUserId()));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, SecurityUtil.authUserId()), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal create = MealTestData.getNew();
        ResultActions action = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(create)));

        Meal returned = readFromJson(action, Meal.class);
        create.setId(returned.getId());
        MEAL_MATCHER.assertMatch(returned, create);
        MEAL_MATCHER.assertMatch(service.getAll(START_SEQ), create, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGetAll() throws Exception {
        perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getTos(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    public void testGetBetween() throws Exception {
        perform(get(REST_URL + "between?startDateTime=2015-05-30T07:00&endDateTime=2015-05-31T11:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJsonArray(
                        MealsUtil.createTo(MEAL4, true),
                        MealsUtil.createTo(MEAL1, false)));
    }
}