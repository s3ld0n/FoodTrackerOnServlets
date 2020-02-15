package org.training.food.tracker.service.defaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.DayDao;
import org.training.food.tracker.dao.jdbc.DayDaoJDBC;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.DayBuilder;
import org.training.food.tracker.service.DayService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayServiceDefault implements DayService {

    private DayDao dayDao;

    private static final Logger LOG = LoggerFactory.getLogger(DayServiceDefault.class.getName());

    public DayServiceDefault() {
        this.dayDao = new DayDaoJDBC();
    }

    public Day getCurrentDayOfUser(User user) throws DaoException {
        LOG.debug("getCurrentDayOfUser()");
        Day day;
        try {
            LOG.debug("getCurrentDayOfUser() :: finding current day");
            day = dayDao.findByUserAndDate(user, LocalDate.now());
        } catch (DaoException e) {
            LOG.debug("getCurrentDayOfUser() :: current day finding has failed, creating a new day");
            day = DayBuilder.instance()
                          .date(LocalDate.now())
                          .consumedFoods(new ArrayList<>())
                          .caloriesConsumed(new BigDecimal(0))
                          .exceededCalories(new BigDecimal(0))
                          .isDailyNormExceeded(false)
                          .user(user).build();
            dayDao.create(day);
        }
        LOG.debug("getCurrentDayOfUser() :: day was successfully found");
        return day;
    }

    public void updateDay(Day day, ConsumedFood consumedFood) throws DaoException {
        LOG.debug("updateDay() :: adding calories ");
        addConsumedCalories(day, consumedFood.getTotalCalories());

        LOG.debug("updateDay() :: calories of the day after update {}", day.getCaloriesConsumed());
        setExceededCaloriesIfAny(day);
        dayDao.update(day);
    }

    private void addConsumedCalories(Day day, BigDecimal calories) {
        day.setCaloriesConsumed(day.getCaloriesConsumed().add(calories));
    }

    private void setExceededCaloriesIfAny(Day day) {
        BigDecimal exceededCalories = getExceededCalories(day);
        if (exceededCalories.intValue() > 0) {
            day.setDailyNormExceeded(true);
            day.setExceededCalories(exceededCalories);
        }
    }

    private BigDecimal getExceededCalories(Day day) {
        return day.getCaloriesConsumed().subtract(day.getUser().getDailyNormCalories());
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
}
