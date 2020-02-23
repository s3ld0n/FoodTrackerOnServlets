package org.training.food.tracker.controller.servlet.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.service.FoodService;
import org.training.food.tracker.service.defaults.FoodServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/food-list/delete")
public class AdminFoodListDeleteFoodServlet extends HttpServlet {

    private FoodService foodService;
    private static final Logger LOG = LoggerFactory.getLogger(AdminFoodListDeleteFoodServlet.class.getName());

    @Override public void init() throws ServletException {
        foodService = new FoodServiceDefault();
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doPost() :: deleting food");

        try {
            foodService.deleteCommonFoodByName(request.getParameter("name"));
        } catch (DaoException e) {
            LOG.error("Deletion failed - error occurred.");
            request.getRequestDispatcher("/WEB-INF/jsp/errors/error.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/admin/food-list");
    }
}
