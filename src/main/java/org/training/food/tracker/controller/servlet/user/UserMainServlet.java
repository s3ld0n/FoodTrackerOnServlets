package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.ConsumedFoodDaoJDBC;
import org.training.food.tracker.dao.jdbc.DayDaoJDBC;
import org.training.food.tracker.dao.jdbc.FoodDaoJDBC;
import org.training.food.tracker.dto.*;
import org.training.food.tracker.model.ConsumedFood;
import org.training.food.tracker.model.Day;
import org.training.food.tracker.model.User;
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
import java.io.IOException;
import java.util.List;

@WebServlet("/user/main")
public class UserMainServlet extends HttpServlet {

    private FoodService foodService;
    private DayService dayService;
    private UserService userService;
    private ConsumedFoodService consumedFoodService;

    private static final Logger LOG = LoggerFactory.getLogger(UserMainServlet.class.getName());

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


        List<FoodDTO> allCommonFoodDTOs = null;
        List<FoodDTO> userFoodDTOs = null;
        User currentUser = null;
        Day currentDay = null;
        List<ConsumedFoodDTO> consumedFoodDTOs = null;

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
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
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
        request.getRequestDispatcher("/jsp/user/main.jsp").forward(request, response);
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
