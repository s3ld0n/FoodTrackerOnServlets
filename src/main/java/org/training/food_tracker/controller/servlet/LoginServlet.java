package org.training.food_tracker.controller.servlet;

import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.model.Role;
import org.training.food_tracker.model.User;
import org.training.food_tracker.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override public void init() throws ServletException {
        userService = new UserService();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        validateCredentialsAndSendBackIfNot(request, response, username, password);

        User user;

        try {
            user = userService.findByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        if (user.getRole() == Role.USER) {
            response.sendRedirect("main.jsp");
        } else {
            response.sendRedirect("admin/main");
        }

    }

    private void validateCredentialsAndSendBackIfNot(HttpServletRequest request, HttpServletResponse response,
            String username, String password) throws IOException {
        if( username == null || username.equals("") || password == null || password.equals("")  ){
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
