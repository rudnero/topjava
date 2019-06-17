package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMealController.class);

    private final MealService service;

    @Autowired
    public AbstractMealController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        LOG.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void update (Meal meal) {
        LOG.info("update {}", meal);
        assureIdConsistent(meal, meal.getId());
        service.update(meal);
    }

    public void delete (int id) {
        LOG.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get (int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll () {
        LOG.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());

    }

    public List<MealTo> getFiltered (LocalDate start, LocalDate end) {
        LOG.info("getFiltered {} {}", start, end);
        List<Meal> meals = service.getFiltered(SecurityUtil.authUserId(), start, end);
        return MealsUtil.getWithExcess(meals, SecurityUtil.authUserCaloriesPerDay());
    }

}
