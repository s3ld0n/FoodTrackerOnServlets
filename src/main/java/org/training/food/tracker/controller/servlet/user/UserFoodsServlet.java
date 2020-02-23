package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.*;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.FoodBuilder;
import org.training.food.tracker.service.ConsumedFoodService;
import org.training.food.tracker.service.DayService;
import org.training.food.tracker.service.FoodService;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.ConsumedFoodServiceDefault;
import org.training.food.tracker.service.defaults.DayServiceDefault;
import org.training.food.tracker.service.defaults.FoodServiceDefault;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/user/food")
public class UserFoodsServlet extends HttpServlet {

    private FoodService foodService;
    private DayService dayService;
    private UserService userService;
    private ConsumedFoodService consumedFoodService;

    private static final Logger LOG = LoggerFactory.getLogger(UserFoodsServlet.class.getName());

    @Override public void init() throws ServletException {
        foodService = new FoodServiceDefault();
        dayService = new DayServiceDefault();
        userService = new UserServiceDefault();
        consumedFoodService = new ConsumedFoodServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doGet()");
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        List<FoodDTO> allCommonFoodDTOs;
        List<FoodDTO> userFoodDTOs;
        User currentUser;
        Day currentDay;
        List<ConsumedFoodDTO> consumedFoodDTOs;

        try {
            currentUser = userService.findByUsername(userCredentials.getUsername());

            LOG.debug("doGet() :: setting allCommonFoodDTOs");
            allCommonFoodDTOs =
                    DTOConverter.foodsToFoodDTOs(foodService.findAllCommonExcludingPersonalByUserId(currentUser.getId()));

            userFoodDTOs = DTOConverter.foodsToFoodDTOs(foodService.findAllByOwner(currentUser));

            currentDay = dayService.getCurrentDayOfUser(currentUser);

            consumedFoodDTOs = DTOConverter.consumedFoodsToConsumedFoodDTOs(consumedFoodService.findAllByDay(currentDay));
        } catch (DaoException e) {
            LOG.error("doGet() :: error occurred", e);
            request.getRequestDispatcher("/WEB-INF/jsp/errors/error.jsp").forward(request, response);
            return;
        }

        LOG.debug("doGet() :: CURRENT DAY: isDailyNormExceeded = {}", currentDay.isDailyNormExceeded());

        request.setAttribute("allCommonFoodDTOs", allCommonFoodDTOs);

        LOG.debug("doGet() :: setting usersFoodDTOs {}", userFoodDTOs);
        request.setAttribute("userFoodDTOs", userFoodDTOs);

        UserDTO userDTO = DTOConverter.userToUserDTO(currentUser);
        userDTO.setPassword(null);

        LOG.debug("doGet() :: setting userDTO ");
        request.setAttribute("userDTO", userDTO);

        LOG.debug("doGet() :: setting consumedFoodDTOs");
        request.setAttribute("consumedFoodDTOs", consumedFoodDTOs);

        DayDTO dayDTO = DTOConverter.dayToDayDTO(currentDay);
        LOG.debug("doGet() :: setting DayDTO");
        LOG.debug("doGet() :: dayDTO isDailyNormExceeded = {}", dayDTO.isDailyNormExceeded());
        request.setAttribute("dayDTO", dayDTO);

        request.setAttribute("food", new FoodDTO());
        request.setAttribute("loggedUsername", userCredentials.getUsername());
        LOG.debug("doGet() :: forwarding page");
        request.getRequestDispatcher("/WEB-INF/jsp/user/food.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        LOG.debug("doPost() :: validating food from request");
        String name = request.getParameter("name");
        String calories = request.getParameter("calories");

        HttpSession session = request.getSession();

        if (!isValidInput(name, calories)) {
            session.setAttribute("invalidInput",true);
            response.sendRedirect("/user/food");
            return;
        } else {
            session.setAttribute("invalidInput",false);
        }

        LOG.debug("doPost() :: building food from request");
        Food food = buildFood(request);

        User currentUser;
        try {
            LOG.debug("doPost() :: getting current user from DB");
            currentUser = userService.findByUsername(userCredentials.getUsername());
            food.setOwner(currentUser);
            LOG.debug("doPost() :: adding food for current user");
            foodService.create(food);
        } catch (DaoException e) {
            LOG.error("doPost() :: error occurred", e);
            request.getRequestDispatcher("/WEB-INF/jsp/errors/error.jsp").forward(request, response);
        }
        response.sendRedirect("/user/food");
    }

    private boolean setErrorsToSessionOnInvalid(HttpServletRequest request, String name, String calories) {
        boolean invalidCalories = false;
        boolean invalidName = false;

        if (isNotValidCalories(calories)) {
            request.getSession().setAttribute("invalidCalories",true);
            invalidCalories = true;
        }

        if (isNotValidName(name)) {
            request.getSession().setAttribute("invalidName",true);
            invalidName = true;
        }
        return invalidCalories || invalidName;
    }

    private void setCaloriesErrorIfInvalid(HttpServletRequest request, String calories) {
        if (isNotValidCalories(calories)) {
            request.getSession().setAttribute("invalidCalories",true);
        }
    }

    private void setNameErrorIfInvalid(HttpServletRequest request, String name) {
        if (isNotValidName(name)) {
            request.getSession().setAttribute("invalidName",true);
        }
    }

    private boolean isValidInput(String name, String calories) {
        return calories.matches("\\d{1,3}") && name.length() <= 30;
    }

    private boolean isNotValidName(String name ) {
        return name.length() > 30;
    }

    private boolean isNotValidCalories(String calories) {
        return !calories.matches("\\d{1,3}");
    }

    private Food buildFood(HttpServletRequest request) {
        return FoodBuilder.instance()
                       .name(request.getParameter("name"))
                       .calories(new BigDecimal(Double.parseDouble(request.getParameter("calories"))))
                       .build();
    }


    private User findUser(UserCredentials userCredentials) {
        User currentUser = null;
        try {
            currentUser = userService.findByUsername(userCredentials.getUsername());
        } catch (DaoException e) {
            LOG.error("doGet() :: finding user by username has failed");
        }
        return currentUser;
    }
}
