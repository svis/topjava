package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
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
import static ru.javawebinar.topjava.TestUtil.contentJsonArray;
import static ru.javawebinar.topjava.TestUtil.readFromJson;

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
                .andExpect(MEAL_MATCHER.contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, SecurityUtil.authUserId()));
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
        Meal newmeal = MealTestData.getNew();
        ResultActions action = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newmeal)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        int newId = returned.id();
        newmeal.setId(newId);
        MEAL_MATCHER.assertMatch(returned, newmeal);
        MEAL_MATCHER.assertMatch(service.get(newId, SecurityUtil.authUserId()), newmeal);
    }

    @Test
    public void testGetAll() throws Exception {
        perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEALTO_MATCHER.contentJson(MealsUtil.getTos(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    public void testGetBetween() throws Exception {
        perform(get(REST_URL + "filter")
                .param("startDate", "2015-05-30").param("startTime", "07:00")
                .param("endDate", "2015-05-31").param("endTime", "11:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJsonArray(
                        MealsUtil.createTo(MEAL4, true),
                        MealsUtil.createTo(MEAL1, false)));
    }
}