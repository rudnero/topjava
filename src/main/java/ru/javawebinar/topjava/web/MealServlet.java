package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Start MealServlet.");
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 20, 0), "Ужин", 500),

                new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 20, 0), "Ужин", 510)
        );
        List<MealTo> mealsWithExcess = MealsUtil.getFilteredWithExcess(meals, LocalTime.MIN, LocalTime.MAX, 2000);
        LOG.debug(mealsWithExcess.toString());

        req.setAttribute("meals", mealsWithExcess);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
