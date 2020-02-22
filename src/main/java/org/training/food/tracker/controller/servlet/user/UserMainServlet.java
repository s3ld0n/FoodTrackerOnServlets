package org.training.food.tracker.controller.servlet.user;

import org.training.food.tracker.controller.UserCredentials;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/main")
public class UserMainServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");
        request.setAttribute("loggedUsername", userCredentials.getUsername());

        request.getRequestDispatcher("/WEB-INF/jsp/user/main.jsp").forward(request, response);
    }
}
