package org.training.food_tracker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.DayDao;
import org.training.food_tracker.dao.UserDao;
import org.training.food_tracker.dto.ConsumeStatsDTO;
import org.training.food_tracker.model.ConsumedFood;
import org.training.food_tracker.model.Day;
import org.training.food_tracker.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class DayService {

    private DayDao dayDao;

    private static final Logger log = LogManager.getLogger(DayService.class.getName());

    public DayService(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    public Day getCurrentDayOfUser(User user) throws DaoException {

        Day day = null;
        try {
            day = dayDao.findByUserAndDate(user, LocalDate.now());
            sortConsumedFoodByTimeDesc(day.getConsumedFoods());
        } catch (DaoException e) {
            day = Day.builder()
                         .date(LocalDate.now())
                         .consumedFoods(new ArrayList<>())
                         .totalCalories(new BigDecimal(0))
                         .user(user)
                         .build();
            dayDao.create(day);
        }

        return day;
    }

    private void sortConsumedFoodByTimeDesc(List<ConsumedFood> foods) {
        foods.sort((food1, food2) -> (food2.getTime().toSecondOfDay() - food1.getTime().toSecondOfDay()));
    }

    public Map<Day, ConsumeStatsDTO> getDaysToConsumeStatsForUser(User user) {
        return mapDaysToConsumeStats(getAllDaysByUser(user));
    }

    private Map<Day, ConsumeStatsDTO> mapDaysToConsumeStats(List<Day> days) {
        Map<Day, ConsumeStatsDTO> dayToConsumeStats = new LinkedHashMap<>();
        days.forEach(day -> dayToConsumeStats.put(day, getConsumeStatsForDay(day)));
        return dayToConsumeStats;
    }

    public List<Day> getAllDaysByUser(User user) {
        return dayDao.findAllByUserOrderByDateDesc(user);
    }

    public BigDecimal getTotalCaloriesOfDay(Day day) {
        BigDecimal totalCalories = new BigDecimal(0);
        for (ConsumedFood food : day.getConsumedFoods()) {
            totalCalories = totalCalories.add(food.getTotalCalories());
        }
        return totalCalories;
    }

    public ConsumeStatsDTO getConsumeStatsForDay(Day day) {
        log.debug("Getting day statistics for user");

        BigDecimal userDailyNorm = day.getUser().getBiometrics().getDailyNorm();
        log.debug("User's daily norm {}", userDailyNorm);

        BigDecimal currentDayTotalCalories = day.getTotalCalories();
        log.debug("Current day total calories: {}", currentDayTotalCalories);
        boolean isNormExceeded = false;
        BigDecimal exceededCalories;

        if (userDailyNorm.compareTo(currentDayTotalCalories) > 0) {
            exceededCalories = new BigDecimal(0);
        } else {
            exceededCalories = currentDayTotalCalories.subtract(userDailyNorm);
            isNormExceeded = true;
        }
        log.debug("exceeded calories: {}", exceededCalories);

        return ConsumeStatsDTO.builder()
                       .caloriesConsumed(currentDayTotalCalories)
                       .isDailyNormExceeded(isNormExceeded)
                       .exceededCalories(exceededCalories)
                       .build();
    }
}
