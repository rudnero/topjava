package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController{

    public MealRestController(MealService service) {
        super(service);
    }

    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    @Override
    public void update(Meal meal) {
        super.update(meal);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    public List<MealTo> getFiltered(LocalDate start, LocalDate end) {
        return super.getFiltered(start, end);
    }
}