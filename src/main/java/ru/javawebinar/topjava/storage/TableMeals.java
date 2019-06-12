package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TableMeals implements DBMeals {
    private Map<Integer, Meal> table = new ConcurrentHashMap();
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public Meal put(Meal meal) {
        if (meal.getId()==null) {
            meal.setId(counter.getAndIncrement());
        }
        return table.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        return table.get(id);
    }

    @Override
    public void delete(Integer id) {
        table.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return table.values();
    }
}
