package org.training.food.tracker.controller.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.model.Role;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("doPost");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LOG.debug("validating username and password");
        validateCredentialsAndSendBackIfNot(request, response, username, password);

        User user;

        LOG.debug("getting user from DB");
        try {
            user = userService.findByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        Role role = user.getRole();

        LOG.debug("user's role: {}", role);
        setUserToSession(request, user);

        response.sendRedirect(getRedirectForRole(role));

        HashSet<String> loggedUsers = (HashSet<String>) getServletContext().getAttribute("loggedUsers");
        loggedUsers.add(username);

    }

    private String getRedirectForRole(Role role) {
        return (role == Role.USER) ? "user/main" : "admin/main";
    }

    private void validateCredentialsAndSendBackIfNot(HttpServletRequest request, HttpServletResponse response,
            String username, String password) throws IOException {
        if(username == null || username.equals("") || password == null || password.equals("")){
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private void setUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
