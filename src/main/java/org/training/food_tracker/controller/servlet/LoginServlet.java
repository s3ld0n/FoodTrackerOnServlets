package org.training.food_tracker.controller.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.training.food_tracker.controller.Servlet;
import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.model.Role;
import org.training.food_tracker.model.User;
import org.training.food_tracker.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class.getName());

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

        log.debug("doPost");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.debug("validating username and password");
        validateCredentialsAndSendBackIfNot(request, response, username, password);

        User user;

        log.debug("getting user from DB");
        try {
            user = userService.findByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        log.debug("user's role: {}", user.getRole());

        if (user.getRole() == Role.USER) {
            setUserAndRoleToSession(request, user);
            response.sendRedirect("user/main");
        } else {
            setUserAndRoleToSession(request, user);
            response.sendRedirect("admin/main");
        }

        HashSet<String> loggedUsers = (HashSet<String>) getServletContext().getAttribute("loggedUsers");
        loggedUsers.add(username);

    }

    private void validateCredentialsAndSendBackIfNot(HttpServletRequest request, HttpServletResponse response,
            String username, String password) throws IOException {
        if(username == null || username.equals("") || password == null || password.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private void setUserAndRoleToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
