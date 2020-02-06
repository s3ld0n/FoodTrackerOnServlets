package org.training.food.tracker.controller.servlet.admin;

import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.defaults.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class UsersServlet extends HttpServlet {

    private UserService userService;

    @Override public void init() throws ServletException {
        userService = new UserService();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users;

        try {
            users = userService.findAll();
        } catch (DaoException e) {
            e.printStackTrace(); //TODO add logs
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("/jsp/admin/users.jsp").forward(request, response);
    }
}
