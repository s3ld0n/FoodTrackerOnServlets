package org.training.food.tracker.controller.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOG = LogManager.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        LOG.debug("current user in session: {}",  currentUser.getUsername());

        ((HashSet<String>) getServletContext().getAttribute("loggedUsers")).remove(currentUser.getUsername());

        session.setAttribute("user", "");
        response.sendRedirect("/");
    }
}
