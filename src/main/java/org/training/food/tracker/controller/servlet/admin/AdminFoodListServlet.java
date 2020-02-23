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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.training.food.tracker.controller.validator.FoodValidator.isValidInput;

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
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        List<FoodDTO> allCommonFoodDTOs = DTOConverter.foodsToFoodDTOs(allCommonFoods);


        request.setAttribute("loggedUsername", userCredentials.getUsername());
        request.setAttribute("allCommonFoodDTOs", allCommonFoodDTOs);

        LOG.debug("doGet() :: forwarding page");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/food-list.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("doPost() :: validating food from request");
        String name = request.getParameter("name");
        String calories = request.getParameter("calories");

        HttpSession session = request.getSession();

        if (!isValidInput(name, calories)) {
            session.setAttribute("invalidInput",true);
            response.sendRedirect("/admin/food-list");
            return;
        } else {
            session.setAttribute("invalidInput",false);
        }

        Food food = buildFood(request);

        try {
            foodService.create(food);
        } catch (DaoException e) {
            LOG.error("Error occurred during all food selection", e);
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
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
