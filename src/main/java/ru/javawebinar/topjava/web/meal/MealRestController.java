package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        LOG.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update (Meal meal, Integer id) {
        LOG.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
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

    public List<MealTo> getFiltered (LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getFiltered {} {} {} {}", startDate, endDate, startTime, endTime);

        return MealsUtil.getFilteredWithExcess(
                service.getFiltered(
                    SecurityUtil.authUserId(),
                    startDate == null ? LocalDate.MIN : startDate,
                    endDate == null ? LocalDate.MAX : endDate
                ),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime==null ? LocalTime.MIN : startTime, endTime==null ? LocalTime.MAX : endTime
        );
    }
}