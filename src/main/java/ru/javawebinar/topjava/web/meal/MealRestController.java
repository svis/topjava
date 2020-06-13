package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

@Controller
public class MealRestController extends AbstractMealController{

    public List<Meal> getAll(int userId){return super.getAll(userId);}

    public Meal get(int id){
        return super.get(id);
    }

    public Meal create(Meal meal){
        return super.create(meal);
    }

    public void delete(int id){
        super.delete(id);
    }

    public void update(Meal meal, int id){
        super.update(meal, id);
    }

}