package org.training.food_tracker.controller.servlet.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.dao.FoodDao;
import org.training.food_tracker.dto.FoodDTO;
import org.training.food_tracker.model.User;
import org.training.food_tracker.service.ConsumedFoodService;
import org.training.food_tracker.service.FoodService;
import org.training.food_tracker.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/main")
public class UserMainServlet extends HttpServlet {

    private UserService userService;
    private FoodService foodService;

    private static final Logger log = LogManager.getLogger(UserMainServlet.class.getName());

    @Override public void init() throws ServletException {
        userService = new UserService();
        foodService = new FoodService(new FoodDao(), new ConsumedFoodService());
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        request.setAttribute("food", new FoodDTO());
        try {
            log.debug("setting allCommonFood");
            request.setAttribute("allCommonFood",
                    foodService.findAllCommonExcludingPersonalByUserIdInDTO(currentUser.getId()));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        request.setAttribute("usersFoodDTOs", foodService.findAllByOwnerInDTOs(currentUser));


        log.debug("setting userDTO {}", userDTO);
        model.addAttribute("userDTO", userDTO);

        request.getRequestDispatcher("/jsp/user/main.jsp").forward(request, response);
    }
}
