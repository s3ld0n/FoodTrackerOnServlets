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
import java.util.List;

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
        List<FoodDTO> allCommonFoodDTOs = null;
        List<FoodDTO> userFoodDTOs = null;

        try {
            LOG.debug("doGet() :: setting allCommonFoodDTOs");
            allCommonFoodDTOs =
                    DTOConverter.foodsToFoodDTOs(foodService.findAllCommonExcludingPersonalByUserId(currentUser.getId()));

            userFoodDTOs = DTOConverter.foodsToFoodDTOs(foodService.findAllByOwner(currentUser));
        } catch (DaoException e) {
            LOG.error("doGet() :: error occurred", e);
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }

        request.setAttribute("allCommonFoodDTOs", allCommonFoodDTOs);

        LOG.debug("doGet() :: setting usersFoodDTOs {}", userFoodDTOs);
        request.setAttribute("userFoodDTOs", userFoodDTOs);

        UserDTO userDTO = DTOConverter.userToUserDTO(currentUser);
        LOG.debug("doGet() :: setting userDTO ");
        request.setAttribute("userDTO", userDTO);

        LOG.debug("doGet() :: forwarding page");
        request.getRequestDispatcher("/jsp/user/main.jsp").forward(request, response);
    }
}
