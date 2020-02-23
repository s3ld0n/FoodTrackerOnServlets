package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.ConsumedFoodBuilder;
import org.training.food.tracker.model.builder.DayBuilder;
import org.training.food.tracker.service.ConsumedFoodService;
import org.training.food.tracker.service.DayService;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.ConsumedFoodServiceDefault;
import org.training.food.tracker.service.defaults.DayServiceDefault;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;

@WebServlet("/user/food/consume")
public class UserConsumeFoodServlet extends HttpServlet {

    private ConsumedFoodService consumedFoodService;
    private DayService dayService;
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserConsumeFoodServlet.class.getName());

    @Override public void init() {
        consumedFoodService = new ConsumedFoodServiceDefault();
        dayService = new DayServiceDefault();
        userService = new UserServiceDefault();
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("doPost() :: getting userCredentials from session");
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");


        ConsumedFood consumedFood = buildConsumedFood(request);

        User user = null;
        Day currentDay = null;

        try {
            user = userService.findByUsername(userCredentials.getUsername());
            currentDay = dayService.getCurrentDayOfUser(user);

            LOG.debug("doPost() :: calculating calories by amount");
            consumedFoodService.calculateCaloriesByAmount(consumedFood);

            LOG.debug("doPost() :: consumed food calories after recalculation {}", consumedFood.getTotalCalories());
            consumedFoodService.registerConsumption(consumedFood);

            dayService.updateDay(currentDay, consumedFood);
        } catch (DaoException e) {
            LOG.error("Error occurred");
            request.getRequestDispatcher("/WEB-INF/jsp/errors/500.jsp").forward(request, response);
            return;

        }
        response.sendRedirect("/user/food");
    }

    private ConsumedFood buildConsumedFood(HttpServletRequest request) {
        return ConsumedFoodBuilder.instance()
                                .amount(new BigDecimal(Double.parseDouble(request.getParameter("amount"))))
                                .name(request.getParameter("name"))
                                .totalCalories(new BigDecimal(Double.parseDouble(request.getParameter("totalCalories"))))
                                .time(LocalTime.now())
                                .build();
    }
}
