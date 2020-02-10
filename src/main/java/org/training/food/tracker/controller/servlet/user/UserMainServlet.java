package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.ConsumedFoodDao;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.jdbc.ConsumedFoodDaoJDBC;
import org.training.food.tracker.dao.jdbc.DayDaoJDBC;
import org.training.food.tracker.dao.jdbc.FoodDaoJDBC;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.FoodService;
import org.training.food.tracker.service.defaults.ConsumedFoodServiceDefault;
import org.training.food.tracker.service.defaults.DayServiceDefault;
import org.training.food.tracker.service.defaults.FoodServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/main")
public class UserMainServlet extends HttpServlet {

    private FoodService foodService;
    private DayServiceDefault dayService;
    private ConsumedFoodDao consumedFoodDao;

    private static final Logger LOG = LoggerFactory.getLogger(UserMainServlet.class.getName());

    @Override public void init() throws ServletException {
        foodService = new FoodServiceDefault(new FoodDaoJDBC(), new ConsumedFoodServiceDefault());
        dayService = new DayServiceDefault(new DayDaoJDBC());
        consumedFoodDao = new ConsumedFoodDaoJDBC();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doGet()");
        User currentUser = (User) request.getSession().getAttribute("user");

        request.setAttribute("food", new FoodDTO());
        try {
            LOG.debug("doGet() :: setting allCommonFoodDTOs");
            request.setAttribute("doGet() :: allCommonFoodDTOs",
                    DTOConverter.foodsToFoodDTOs(foodService.findAllCommonExcludingPersonalByUserId(currentUser.getId())));

            LOG.debug("doGet() :: making ConsumptionDataDTO from consumed food of the current day");
            request.setAttribute("ConsumptionDataDTO",
                    DTOConverter.buildConsumptionDataDTO(
                            dayService.getCurrentDayOfUser(currentUser).getConsumedFoods(),
                            currentUser
                    ));

            LOG.debug("doGet() :: setting usersFoodDTOs");
            request.setAttribute("usersFoodDTOs",
                    DTOConverter.foodsToFoodDTOs(foodService.findAllByOwner(currentUser)));

        } catch (DaoException e) {
            e.printStackTrace();
        }


        UserDTO userDTO = DTOConverter.userToUserDTO(currentUser);
        LOG.debug("doGet() :: setting userDTO {}", userDTO);
        request.setAttribute("userDTO", userDTO);

        request.getRequestDispatcher("/jsp/user/main.jsp").forward(request, response);
    }
}
