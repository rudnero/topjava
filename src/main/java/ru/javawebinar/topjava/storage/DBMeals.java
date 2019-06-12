package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import java.util.Collection;

public interface DBMeals {
    Meal put(Meal meal);
    Meal get(Integer id);
    void delete(Integer id);
    Collection<Meal> getAll();
}
