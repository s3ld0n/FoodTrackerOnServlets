package org.training.food_tracker.controller.servlet.user;

import org.training.food_tracker.dto.FoodDTO;
import org.training.food_tracker.model.User;
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

    @Override public void init() throws ServletException {
        userService = new UserService();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        request.setAttribute("food", new FoodDTO());


        request.getRequestDispatcher("/jsp/user/main.jsp").forward(request, response);
    }
}
