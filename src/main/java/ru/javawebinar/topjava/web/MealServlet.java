package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.DBMeals;
import ru.javawebinar.topjava.storage.TableMeals;
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

    //Servlet parameters
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_ID = "id";

    //Servlet actions
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_ADD= "add";

    private static String INSERT_OR_EDIT = "meal.jsp";

    private DBMeals tableMeals;

    private static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2019, Month.JUNE, 01, 20, 0), "Ужин", 500),

            new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2019, Month.JUNE, 02, 20, 0), "Ужин", 510)
    );

    public MealServlet() {
        super();
        tableMeals = new TableMeals();
        meals.forEach(meal -> tableMeals.put(meal));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Start MealServlet.doGet()");

        String action = req.getParameter(PARAMETER_ACTION);
        if (action==null) {
            action = "";
        }
        Integer id;

        switch (action) {
            case ACTION_DELETE:
                id = Integer.parseInt(req.getParameter(PARAMETER_ID));
                tableMeals.delete(id);
                LOG.debug("Meal was deleted by id=" + id);
            case ACTION_ADD:
                Meal meal_add = new Meal(LocalDateTime.now(), "", 0);
                req.setAttribute("meal", meal_add);
                req.getRequestDispatcher(INSERT_OR_EDIT).forward(req, resp);
            case ACTION_EDIT:
                id = Integer.parseInt(req.getParameter(PARAMETER_ID));
                Meal meal = tableMeals.get(id);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher(INSERT_OR_EDIT).forward(req, resp);
            default:
                List<MealTo> mealsWithExcess = MealsUtil.getFilteredWithExcess(tableMeals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                req.setAttribute("meals", mealsWithExcess);
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dt = req.getParameter("dt");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        String sid = req.getParameter(PARAMETER_ID);

        Meal meal;
        if(sid == null | sid.equals("")) {
            meal = new Meal(LocalDateTime.parse(dt), description, Integer.parseInt(calories));
        } else {
            Integer id = Integer.parseInt(sid);
            meal = new Meal(id, LocalDateTime.parse(dt), description, Integer.parseInt(calories));
        }

        tableMeals.put(meal);

        List<MealTo> mealsWithExcess = MealsUtil.getFilteredWithExcess(tableMeals.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", mealsWithExcess);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
