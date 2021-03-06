package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.User;
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

@WebServlet("/user/food/delete")
public class UserDeleteFoodServlet extends HttpServlet {

    private FoodService foodService;
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserFoodsServlet.class.getName());

    @Override public void init() {
        foodService = new FoodServiceDefault();
        userService = new UserServiceDefault();
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doPost() :: getting userCredentials from session");
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");


        String foodName = request.getParameter("name");

        try {
            LOG.debug("doPost() :: finding user by username");
            User user = userService.findByUsername(userCredentials.getUsername());

            LOG.debug("doPost() :: deleting user by food name and user");
            foodService.deleteByNameAndUserId(foodName, user);
        } catch (DaoException e) {
            LOG.error("doPost() :: food deletion failed");
            return;
        }

        LOG.debug("doPost() :: food {} was successfully deleted", foodName);
        response.sendRedirect("/user/food");
    }
}
