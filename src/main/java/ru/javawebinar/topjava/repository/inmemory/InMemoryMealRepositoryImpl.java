package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    //private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, Integer userId) {
        LOG.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        } else if (!meal.getUserId().equals(userId)) {
            return null;
        }

        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, HashMap::new);
        userMeals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete {}", id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get {}", id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals!=null ? userMeals.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll");
        return getSortedFilteredList(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate start, LocalDate end) {
        return getSortedFilteredList(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), start, end));
    }

    private List<Meal> getSortedFilteredList(Integer userId, Predicate<Meal> filter) {
        LOG.info("getSortedFilteredList {}", userId);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? new ArrayList<>() : userMeals.values().stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .filter(filter)
                .collect(Collectors.toList());
    }
}

