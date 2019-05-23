package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        System.out.println("---Foreach---");
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
        System.out.println("---Stream---");
        System.out.println(getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapUserCaloriesPerDay = mealList.stream()
                .collect(
                        Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum)
                );
        return mealList.stream()
                .filter(userMeal->TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal->new UserMealWithExceed(userMeal.getDateTime() , userMeal.getDescription(), userMeal.getCalories(),mapUserCaloriesPerDay.get(userMeal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapUserCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            mapUserCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime localDateTime = meal.getDateTime();
            if (TimeUtil.isBetween(localDateTime.toLocalTime(), startTime, endTime)) {
                UserMealWithExceed mealWithExceed =
                        new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mapUserCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                mealWithExceeds.add(mealWithExceed);
            }
        }
        return mealWithExceeds;
    }
}
