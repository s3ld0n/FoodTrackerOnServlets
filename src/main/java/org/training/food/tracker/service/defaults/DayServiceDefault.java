package org.training.food.tracker.service.defaults;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.DayDao;
import org.training.food.tracker.dto.ConsumptionDataDTO;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.DayService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DayServiceDefault implements DayService {

    private DayDao dayDao;

    private static final Logger LOG = LoggerFactory.getLogger(DayServiceDefault.class.getName());

    public DayServiceDefault(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    public Day getCurrentDayOfUser(User user) throws DaoException {
        LOG.debug("getCurrentDayOfUser()");
        Day day;
        try {
            LOG.debug("getCurrentDayOfUser() :: finding current day");
            day = dayDao.findByUserAndDate(user, LocalDate.now());
//            sortConsumedFoodByTimeDesc(day.getConsumedFoods());
        } catch (DaoException e) {
            LOG.debug("getCurrentDayOfUser() :: current day finding has failed, creating a new day");
            day = Day.builder()
                          .date(LocalDate.now())
                          .consumedFoods(new ArrayList<>())
                          .totalCalories(new BigDecimal(0))
                          .user(user).build();
            dayDao.create(day);
        }
        LOG.debug("getCurrentDayOfUser() :: day was successfully found");
        return day;
    }

    private void sortConsumedFoodByTimeDesc(List<ConsumedFood> foods) {
        foods.sort((food1, food2) -> (food2.getTime().toSecondOfDay() - food1.getTime().toSecondOfDay()));
    }

    //    public Map<Day, ConsumeStatsDTO> getDaysToConsumeStatsForUser(User user) {
    //        return mapDaysToConsumeStats(getAllDaysByUser(user));
    //    }

    private Map<Day, ConsumptionDataDTO> mapDaysToConsumeStats(List<Day> days) {
        Map<Day, ConsumptionDataDTO> dayToConsumeStats = new LinkedHashMap<>();
        days.forEach(day -> dayToConsumeStats.put(day, getConsumeStatsForDay(day)));
        return dayToConsumeStats;
    }

    public List<Day> getAllDaysByUser(User user) throws DaoException {
        return dayDao.findAllByUserOrderByDateDesc(user);
    }

    public BigDecimal getTotalCaloriesOfDay(Day day) {
        BigDecimal totalCalories = new BigDecimal(0);
        for (ConsumedFood food : day.getConsumedFoods()) {
            totalCalories = totalCalories.add(food.getTotalCalories());
        }
        return totalCalories;
    }

    public ConsumptionDataDTO getConsumeStatsForDay(Day day) {
        LOG.debug("Getting day statistics for user");

        BigDecimal userDailyNorm = day.getUser().getDailyNormCalories();
        LOG.debug("User's daily norm {}", userDailyNorm);

        BigDecimal currentDayTotalCalories = day.getTotalCalories();
        LOG.debug("Current day total calories: {}", currentDayTotalCalories);
        boolean isNormExceeded = false;
        BigDecimal exceededCalories;

        if (userDailyNorm.compareTo(currentDayTotalCalories) > 0) {
            exceededCalories = new BigDecimal(0);
        } else{
            exceededCalories = currentDayTotalCalories.subtract(userDailyNorm);
            isNormExceeded = true;
        }
        LOG.debug("exceeded calories: {}", exceededCalories);

        return ConsumptionDataDTO.builder().caloriesConsumed(currentDayTotalCalories).isDailyNormExceeded(isNormExceeded)
                       .exceededCalories(exceededCalories).build();
    }
}
