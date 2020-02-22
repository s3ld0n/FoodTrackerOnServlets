package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.FoodBuilder;
import org.training.food.tracker.service.FoodService;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.FoodServiceDefault;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/user/food/add")
public class UserAddFoodServlet extends HttpServlet {

    private FoodService foodService;
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserFoodsServlet.class.getName());

    @Override public void init() throws ServletException {
        foodService = new FoodServiceDefault();
        userService = new UserServiceDefault();
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

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
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
        response.sendRedirect("/user/main");
    }

    private Food buildFood(HttpServletRequest request) {
        return FoodBuilder.instance()
                .name(request.getParameter("name"))
                .calories(new BigDecimal(Double.parseDouble(request.getParameter("calories"))))
                .build();
    }
}
