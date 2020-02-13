package org.training.food.tracker.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
        LOG.debug("doGet :: logged user before invalidate session {}", userCredentials);

        LOG.debug("doGet :: invalidate session");
        session.invalidate();

        HashSet<UserCredentials> loggedUsers = ((HashSet<UserCredentials>) getServletContext().getAttribute("loggedUsers"));

        loggedUsers.remove(userCredentials);
        LOG.debug("doGet :: logged users :: {}", loggedUsers.toArray());
        response.sendRedirect("/");
    }
}
