package org.training.food.tracker.controller.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Role;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class.getName());

    private UserService userService;

    @Override public void init() throws ServletException {
        userService = new UserServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("doPost()");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LOG.debug("doPost() :: validating username and password");
        if (isNotValidCredentials(username, password)) {
            request.setAttribute("invalidCredentials", "Invalid Credentials!");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        User user;

        LOG.debug("doPost() :: getting user from DB");
        try {
            user = userService.findByUsername(username);
        } catch (DaoException e) {
            LOG.error("error occurred during selection of user");
            request.setAttribute("invalidCredentials", "Invalid Credentials!");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        Role role = user.getRole();

        LOG.debug("doPost() :: creating userCredentials");
        UserCredentials userCredentials = new UserCredentials(username, role.toString());

        LOG.debug("doPost() :: placing userCredentials into session");
        placeUserCredentialsIntoSession(request, userCredentials);

        LOG.debug("doPost() :: placing userCredentials into context");
        addUserToContext(request, userCredentials);

        LOG.debug("doPost() :: sending redirect based on role");
        response.sendRedirect(getRedirectForRole(role));
    }

    private String getRedirectForRole(Role role) {
        return (role == Role.USER) ? "user/main" : "admin/main";
    }

    private boolean isNotValidCredentials(String username, String password) {
        return username == null || username.equals("") || password == null || password.equals("");
    }

    private void placeUserCredentialsIntoSession(HttpServletRequest request, UserCredentials userCredentials) {
        request.getSession().setAttribute("userCredentials", userCredentials);
    }

    private void addUserToContext(HttpServletRequest request, UserCredentials userCredentials) {
        ServletContext context = request.getServletContext();
        HashSet<UserCredentials> loggedUsers = (HashSet<UserCredentials>) context.getAttribute("loggedUsers");
        loggedUsers.add(userCredentials);
    }
}
