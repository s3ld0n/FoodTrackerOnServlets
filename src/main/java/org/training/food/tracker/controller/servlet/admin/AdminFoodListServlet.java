package org.training.food.tracker.controller.servlet.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.FoodDTO;
import org.training.food.tracker.model.Food;
import org.training.food.tracker.model.builder.FoodBuilder;
import org.training.food.tracker.service.FoodService;
import org.training.food.tracker.service.defaults.FoodServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/food-list")
public class AdminFoodListServlet extends HttpServlet {

    private FoodService foodService;

    private static final Logger LOG = LoggerFactory.getLogger(AdminFoodListServlet.class.getName());

    @Override public void init() throws ServletException {
        foodService = new FoodServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        LOG.debug("doGet()");
        List<Food> allCommonFoods;

        LOG.debug("doGet() :: selecting all common food");
        try {
            allCommonFoods = foodService.findAllCommon();
        } catch (DaoException e) {
            LOG.error("Error occurred during all food selection", e);
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        List<FoodDTO> allCommonFoodDTOs = DTOConverter.foodsToFoodDTOs(allCommonFoods);


        request.setAttribute("loggedUsername", userCredentials.getUsername());
        request.setAttribute("allCommonFoodDTOs", allCommonFoodDTOs);

        LOG.debug("doGet() :: forwarding page");
        request.getRequestDispatcher("/jsp/admin/food_list.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doGet() :: adding food to common");
        LOG.debug("doGet() :: getting food from request");

        Food food = buildFood(request);

        try {
            foodService.create(food);
        } catch (DaoException e) {
            LOG.error("Error occurred during all food selection", e);
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/admin/food-list");
    }

    private Food buildFood(HttpServletRequest request) {
        return FoodBuilder.instance()
                            .name(request.getParameter("name"))
                            .calories(new BigDecimal(Double.parseDouble(request.getParameter("calories"))))
                            .build();
    }
}
