package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        List<UserMealWithExceed> result = new ArrayList<UserMealWithExceed>();
        result = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        for (UserMealWithExceed meal : result) {
            System.out.println(meal.getDateTime() + " | " + meal.getDescription() + " | " + meal.getCalories() + " | " + meal.isExceed());
        }

//        .toLocalDate();
//        .toLocalTime();


    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        List<UserMealWithExceed> result = new ArrayList<UserMealWithExceed>();
        Map<LocalDate, Integer> daily = new HashMap<>();


        // creating map with total calories per day
        for (UserMeal userMeal : mealList) {
            if (daily.get(userMeal.getDateTime().toLocalDate()) != null) {
                daily.put(userMeal.getDateTime().toLocalDate(), daily.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            } else {
                daily.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

//        for (Map.Entry<LocalDate, Integer> entry : daily.entrySet()) {
//            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
//        }


        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), daily.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }


        return result;
    }
}
